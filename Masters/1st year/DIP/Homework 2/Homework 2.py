import cv2
import sys
import numpy as np
import os

def cautbin(x):
    st=0
    dr=len(starts)-1
    ans=-1
    while st<=dr:
        mij=(st+dr)//2
        if starts[mij]<=x:
            ans=mij
            st=mij+1
        else:
            dr=mij-1
    return ans

results = dict()
print("Enter the number of gray shades to apply:")
numberOfShades = int(input())        
for filename in os.listdir():
    if filename.endswith('.jpg'):
        image_bgr = cv2.imread(filename)
        image_gray = cv2.imread(filename,cv2.IMREAD_GRAYSCALE)
        shape = image_bgr.shape
        shape = (shape[0], shape[1], 1)
        image_gray1 = np.average(image_bgr, axis=2)
        image_gray2_1 = np.average(image_bgr, axis=2, weights=[0.11, 0.59, 0.3])
        image_gray2_2 = np.average(image_bgr, axis=2, weights=[0.0722, 0.7152, 0.2126])
        image_gray2_3 = np.average(image_bgr, axis=2, weights=[0.114, 0.587, 0.299])
        image_gray3 = (np.max(image_bgr, axis=2) + np.min(image_bgr, axis=2))/2
        image_gray4_1 = np.max(image_bgr,axis=2)
        image_gray4_2 = np.min(image_bgr,axis=2)
        image_gray5_1 = image_bgr[:,:,0]
        image_gray5_2 = image_bgr[:,:,1]
        image_gray5_3 = image_bgr[:,:,2]
        image_gray6 = np.zeros(image_gray2_1.shape)
        image_gray_to_bgr = cv2.cvtColor(image_gray,cv2.COLOR_GRAY2BGR)
        remainder = 255%numberOfShades
        starts=[]
        starts.append(0)
        for i in range(numberOfShades-1):
            if remainder!=0:
                starts.append(starts[-1]+255//numberOfShades+1)
                remainder-=1
            else:
                starts.append(starts[-1]+255//numberOfShades)
        for i in range(image_gray6.shape[0]):
            for j in range(image_gray6.shape[1]):
                shadeIndex = cautbin(image_gray2_1[i][j])
                shade = 0
                if shadeIndex==len(starts)-1:
                    shade=(255+starts[shadeIndex])//2
                else:
                    shade=(starts[shadeIndex+1]+starts[shadeIndex])//2
                image_gray6[i][j]=shade
        image_gray7 = np.copy(image_gray2_1)
        for i in range(1,image_gray7.shape[0]-1):
            for j in range(0,image_gray7.shape[1]-1):
                shadeIndex = cautbin(image_gray2_1[i][j])
                shade=0
                if shadeIndex==len(starts)-1:
                    shade=(255+starts[shadeIndex])//2
                else:
                    shade=(starts[shadeIndex+1]+starts[shadeIndex])//2
                err = image_gray7[i][j]-shade
                image_gray7[i+1][j]+=7.0/16*err
                image_gray7[i-1][j+1]+=3.0/16*err
                image_gray7[i][j+1]+=5.0/16*err
                image_gray7[i+1][j+1]+=1.0/16*err
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-1.jpg']=image_gray1
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-2_1.jpg']=image_gray2_1
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-2_2.jpg']=image_gray2_2
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-2_3.jpg']=image_gray2_3
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-3.jpg']=image_gray3
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-4_1.jpg']=image_gray4_1
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-4_2.jpg']=image_gray4_2
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-5_1.jpg']=image_gray5_1
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-5_2.jpg']=image_gray5_2
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-5_3.jpg']=image_gray5_3
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-6.jpg']=image_gray6
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-7.jpg']=image_gray7
        results[os.getcwd()+'/results/'+filename.split('.')[0]+'-8.jpg']=image_gray_to_bgr
for k in results:
    print(k)
    cv2.imwrite(k,results[k])
