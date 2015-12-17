import sqlite3
import sys
def printProfile(skypeDB):
    conn = sqlite3.connect(skypeDB)
    c = conn.cursor()
    c.execute("SELECT datetime(timestamp,'unixepoch'),dialog_partner,author,body_xml FROM Messages;")        
    print ("--Found Messages--")
    for row in c:
        try:
            if 'parlist' not in str(row[3]):
                if str(row[1]) != str(row[2]):
                    msgDirection = "To " + str(row[1]) + ": "
                else:
                    msgDirection = "From " + str(row[2]) + ": "
                print ("Time: " + str(row[0]) + " " + msgDirection + str(row[3]))
        except:
            pass
def main():
    skypeDB = "C:\\sqlite\\main.db"
    printProfile(skypeDB)
if __name__ == "__main__":
    main()
