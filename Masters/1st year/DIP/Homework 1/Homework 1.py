import cv2
import sys
import numpy as np
import os

results = dict()
for filename in os.listdir():
    if filename.endswith('.jpg'):
        image_bgr = cv2.imread(filename)
        image_hsv = cv2.cvtColor(image_bgr,cv2.COLOR_BGR2HSV)
        image_ycc = cv2.cvtColor(image_bgr,cv2.COLOR_BGR2YCrCb)
        new_image1 = np.zeros(image_bgr.shape,dtype=int)
        new_image2 = np.zeros(image_bgr.shape,dtype=int)
        new_image3 = np.zeros(image_bgr.shape,dtype=int)
        new_image4 = np.zeros(image_bgr.shape,dtype=int)
        new_image5 = np.zeros(image_bgr.shape,dtype=int)
        new_image6 = np.zeros(image_bgr.shape,dtype=int)
        new_image7 = np.zeros(image_bgr.shape,dtype=int)
        for i in range(image_bgr.shape[0]):
            for j in range(image_bgr.shape[1]):
                b, g, r = image_bgr[i][j][0], image_bgr[i][j][1], image_bgr[i][j][2]
                h, s, v = image_hsv[i][j][0], image_hsv[i][j][1], image_hsv[i][j][2]
                #y,cr,cb = 0.299*r+0.587*g+0.114+b, -0.1687*b-0.3313*g+0.5*b+128, 0.5*r-0.4187*g-0.0813*b+128
                y,cr,cb = image_ycc[i][j][0], image_hsv[i][j][1], image_ycc[i][j][2]
                if r>95 and g>40 and b>20 and np.max(image_bgr[i][j])-np.min(image_bgr[i][j])>15 and abs(r-g)>15 and r>g and r>b:
                    new_image1[i][j][0]=new_image1[i][j][1]=new_image1[i][j][2]=255
                if r/g>1.185 and (r*b)/(r+g+b)**2>0.107 and (r*g)/(r+g+b)**2>0.112:
                    new_image2[i][j][0]=new_image2[i][j][1]=new_image2[i][j][2]=255
                if v>=0.4 and 0.2<s<0.6 and (0<h<25 or 335<h<=360):
                    new_image3[i][j][0]=new_image3[i][j][1]=new_image3[i][j][2]=255
                if 0<=h<=50 and 0.23<=s<=0.68 and 0.35<=v<=1:
                    new_image4[i][j][0]=new_image4[i][j][1]=new_image4[i][j][2]=255
                if (0<=h<=50 or 340<=h<=360) and s>=0.2 and v>=0.35:
                    new_image5[i][j][0]=new_image5[i][j][1]=new_image5[i][j][2]=255
                if y>80 and 85<cb<135 and 135<cr<180:
                    new_image6[i][j][0]=new_image6[i][j][1]=new_image6[i][j][2]=255
                if cr<=1.5862*cb+20 and cr>=0.3448*cb+76.2069 and cr>=-4.5652*cb+234.5652 and cr<=-1.15*cb+301.75 and cr<=-2.2857*cb+432.85:
                    new_image7[i][j][0]=new_image7[i][j][1]=new_image7[i][j][2]=255
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-1.jpg']=new_image1
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-2.jpg']=new_image2
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-3.jpg']=new_image3
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-4.jpg']=new_image4
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-5.jpg']=new_image5
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-6.jpg']=new_image6
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-7.jpg']=new_image7
        print(filename)
for k in results:
    print(k)
    cv2.imwrite(k,results[k])
