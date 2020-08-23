import json
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import requests
import base64
from uuid import getnode as get_mac


mac = get_mac()
email_message = "code_run"
Results = open("Error.txt", "r")
body = Results.readline()+ "\n " +str(mac)
client_email = "ishudon1947@gmail.com"
email_subject = "TEENPATTI_ERROR"
user = "teenpattibotfather@gmail.com"
pwd = "Pattikajadu@123"


def send_email(user, pwd, recipient, subject, body):
    import smtplib

    FROM = user
    TO = recipient if isinstance(recipient, list) else [recipient]
    SUBJECT = subject
    TEXT = body

    # Prepare actual message
    message = """From: %s\nTo: %s\nSubject: %s\n\n%s
    """ % (FROM, ", ".join(TO), SUBJECT, TEXT)
    try:
        server = smtplib.SMTP("smtp.gmail.com", 587)
        server.ehlo()
        server.starttls()
        server.login(user, pwd)
        server.sendmail(FROM, TO, message)
        server.close()
    except: 
        print ("failed to send mail")

send_email(user, pwd, client_email, email_subject, body)










