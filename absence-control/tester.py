#!/usr/bin/python3
from urllib import request,parse
import sys
myURL="http://127.0.0.1:8080/absence-control/"
try:
	otvet=request.urlopen(myURL)
	mytext1=otvet.readlines()
	for line in mytext1:
		if "searchAbsence" in str(line):
			print ("Приложение запущенно!")
			break



except Exception:
    print ("Ошибка:Приложение не запущено")
    print (sys.exc_info()[1])


        

        
