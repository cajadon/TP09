import gspread
from oauth2client.service_account import ServiceAccountCredentials
from robot.api.deco import keyword
from robot.api import logger


# use creds to create a client to interact with the Google Drive API
scope = ['https://spreadsheets.google.com/feeds','https://www.googleapis.com/auth/drive']
creds = ServiceAccountCredentials.from_json_keyfile_name("C:\\Users\\ianub\\OneDrive\\Documents\\NetBeansProjects\\Selenium\\Client_secret.json", scope)
client = gspread.authorize(creds)

sheet = client.open("Teen_Patti_Data").sheet1

cardsA = []
cardsB = []
Results = open("Results.txt", "r")
roundID = Results.readline()
date = Results.readline()
time = Results.readline()
winner = Results.readline()
cardsA.append(Results.readline())
cardsA.append(Results.readline())
cardsA.append(Results.readline())
cardsB.append(Results.readline())
cardsB.append(Results.readline())
cardsB.append(Results.readline())

print("python_comes")

column_index = {
    "Serial No.": 1,
    "Date": 2,
    "Time": 3,
    "Round ID": 4,
    "CardsA[0]": 5,
    "CardsA[1]": 6,
    "CardsA[2]": 7,
    "CardsB[0]": 8,
    "CardsB[1]": 9,
    "CardsB[2]": 10,
    "Winner": 11
}

def update_sheet(row, column, data):
    sheet.update_cell(row, column, data)


def get_sheet_data(row, column):
    return sheet.cell(row, column).value


def get_last_serial_number():
    values_list = sheet.col_values(1)
    last_index = len(values_list)
    return values_list[last_index-1]

def get_last_serial_number():
    serial_number_cell = sheet.find("Serial No.")
    serial_number_column = serial_number_cell.col
    serial_number_row = serial_number_cell.row
    values_list = sheet.col_values(serial_number_column)
    last_index = len(values_list)
    print(last_index)
    if last_index == 1:
        return 1
    return last_index-1


def get_last_unfilled_row_number():
    values_list = sheet.col_values(1)
    last_index = len(values_list)
    if last_index == 1:
        return 3
    return last_index+1

def update_serial_number(row_number):
    serial_number = get_last_serial_number()
    update_sheet(row_number, column_index.get("Serial No."), serial_number)


row_number = get_last_unfilled_row_number()
print("row")
print(row_number)
update_serial_number(row_number)
update_sheet(row_number, column_index.get("Date"), date)
update_sheet(row_number, column_index.get("Time"), time)
update_sheet(row_number, column_index.get("Round ID"), roundID)
update_sheet(row_number, column_index.get("CardsA[0]"), cardsA[0])
update_sheet(row_number, column_index.get("CardsA[1]"), cardsA[1])
update_sheet(row_number, column_index.get("CardsA[2]"), cardsA[2])
update_sheet(row_number, column_index.get("CardsB[0]"), cardsB[0])
update_sheet(row_number, column_index.get("CardsB[1]"), cardsB[1])
update_sheet(row_number, column_index.get("CardsB[2]"), cardsB[2])
update_sheet(row_number, column_index.get("Winner"), winner)


