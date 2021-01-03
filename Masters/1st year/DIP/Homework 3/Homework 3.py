import cv2
import sys
import numpy as np
import os
import pytesseract

def apply_shear(img, shear, translation):
    rows,cols = img.shape

    type_border = cv2.BORDER_CONSTANT
    color_border = (255,255)

    new_cols += (shear*new_cols)
    new_rows += (shear*new_rows)

    up_down = int((new_rows-rows)/2); left_right = int((new_cols-cols)/2)

    final_image = cv2.copyMakeBorder(original_image, up_down, up_down,left_right,left_right,type_border, value = color_border)
    rows,cols,ch = final_image.shape

    translat_center_x = -(shear*cols)/2
    translat_center_y = -(shear*rows)/2

    M = np.float64([[0,shear,translation + translat_center_x], [shear,0,translation + translat_center_y]])
    return cv2.warpAffine(final_image , M, (cols,rows),borderMode = type_border, borderValue = color_border)

def apply_rotation(image, angle):
    # grab the dimensions of the image and then determine the
    # center
    (h, w) = image.shape[:2]
    (cX, cY) = (w // 2, h // 2)
    # grab the rotation matrix (applying the negative of the
    # angle to rotate clockwise), then grab the sine and cosine
    # (i.e., the rotation components of the matrix)
    M = cv2.getRotationMatrix2D((cX, cY), -angle, 1.0)
    cos = np.abs(M[0, 0])
    sin = np.abs(M[0, 1])
    # compute the new bounding dimensions of the image
    nW = int((h * sin) + (w * cos))
    nH = int((h * cos) + (w * sin))
    # adjust the rotation matrix to take into account translation
    M[0, 2] += (nW / 2) - cX
    M[1, 2] += (nH / 2) - cY
    # perform the actual rotation and return the image
    return cv2.warpAffine(image, M, (nW, nH))

def apply_resize(img, nH, nW):
    dim = (nW, nH)
    return cv2.resize(img, dim, interpolation=cv2.INTER_AREA)

def apply_blur(img, blurSize):
    return cv2.blur(img, blurSize)

def add_gaussian_noise(img, median, stddev):
    gaussian_noise = np.zeros((img.shape[0], img.shape[1]),dtype=np.uint8)
    cv2.randn(gaussian_noise, median, stddev)
    gaussian_noise = (gaussian_noise*0.5).astype(np.uint8)
    return cv2.add(img, gaussian_noise)

def add_uniform_noise(img, minimum, maximum):
    uniform_noise = np.zeros((img.shape[0], img.shape[1]),dtype=np.uint8)
    cv2.randu(uniform_noise,minimum,maximum)
    uniform_noise = (uniform_noise*0.5).astype(np.uint8)
    return cv2.add(img,uniform_noise)

def generate_images_with_noise_resize_rotations_and_shear():
    for filename in os.listdir():
        if filename.endswith('.jpg') or filename.endswith('.png'):
            image = cv2.imread(filename,cv2.IMREAD_GRAYSCALE)
            amountsForGaussian=[(128,20), (128,50), (200, 20), (50, 20), (75, 50), (128,5)]
            amountsForUniform=[(0,255), (128, 255), (0,127), (50, 100), (75, 100), (100, 200), (250, 255)]
            rotations=[45,90,135,180,270]
            resizeHW=[(128,128), (100,128), (128,100), (255,255), (255,100)]
            amountsForBlur=[(5,5), (3,3), (10,10), (1,1), (100, 100)]
            for i in range(len(amountsForBlur)):
                img=apply_blur(image, amountsForBlur[i])
                name=f'{os.getcwd()}/results/{filename.split(".")[0]}_blur_{amountsForBlur[i]}.{filename.split(".")[1]}'
                cv2.imwrite(name,img)
                print(name)
            for i in range(len(amountsForGaussian)):
                img=add_gaussian_noise(image, amountsForGaussian[i][0], amountsForGaussian[i][1])
                name=f'{os.getcwd()}/results/{filename.split(".")[0]}_gaussian_{amountsForGaussian[i][0]}_{amountsForGaussian[i][1]}.{filename.split(".")[1]}'
                cv2.imwrite(name,img)
                print(name)
            for i in range(len(amountsForUniform)):
                img=add_uniform_noise(image, amountsForUniform[i][0], amountsForUniform[i][1])
                name=f'{os.getcwd()}/results/{filename.split(".")[0]}_uniform_{amountsForUniform[i][0]}_{amountsForUniform[i][1]}.{filename.split(".")[1]}'          
                cv2.imwrite(name,img)
                print(name)
            for i in range(len(rotations)):
                img=apply_rotation(image, rotations[i])
                name=f'{os.getcwd()}/results/{filename.split(".")[0]}_rotation_{rotations[i]}.{filename.split(".")[1]}'                    
                cv2.imwrite(name,img)
                print(name)
            for i in range(len(resizeHW)):
                img=apply_resize(image, resizeHW[i][0], resizeHW[i][1])
                name=f'{os.getcwd()}/results/{filename.split(".")[0]}_resize_{resizeHW[i][0]}_{resizeHW[i][1]}.{filename.split(".")[1]}'
                cv2.imwrite(name,img)
                print(name)

def ocr_without_and_with_preprocessing():
    for filename1 in os.listdir():
        if filename1.endswith('.jpg') or filename1.endswith('.png'):
            image1 = cv2.imread(filename1,cv2.IMREAD_GRAYSCALE)
            filter = np.array([[-1, -1, -1], [-1, 9, -1], [-1, -1, -1]])
            sharpened_img_1 = cv2.filter2D(image1,-1,filter)        
            kernel = np.ones((5,5),np.uint8)
            #sharpened_img_1 = cv2.erode(sharpened_img_1,kernel,iterations = 1)
            print(filename1.split('.')[0]+'\noriginal sharpened: '+str(len(pytesseract.image_to_string(sharpened_img_1).split())))
            print('original: '+str(len(pytesseract.image_to_string(image1).split())))
            for filename2 in os.listdir('./results/'):
                if filename2.startswith(filename1.split('.')[0]):
                    image2 = cv2.imread('./results/'+filename2, cv2.IMREAD_GRAYSCALE)
                    sharpened_img_2=cv2.filter2D(image2,-1,filter)
                    #sharpened_img_2=cv2.erode(sharpened_img_2,kernel,iterations=1)
                    print(f'{filename2.split(".")[0][len(filename1.split(".")[0]):]} sharpened: {len(pytesseract.image_to_string(sharpened_img_2).split())}')
                    print(f'{filename2.split(".")[0][len(filename1.split(".")[0]):]}: {len(pytesseract.image_to_string(image2).split())}')


#generate_images_with_noise_resize_rotations_and_shear()
ocr_without_and_with_preprocessing()
