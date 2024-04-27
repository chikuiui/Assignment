## 1. Introduction

This is a secure and user-friendly PaswordManager application.
This application allow users to store and manage their passwords in a secure and organised manner.

## 2. Installation and Setup

There is an apk_file folder in this github repository contain PasswordManager Apk
Simply download it and install it on your device.

## 3. Usage Guide

 ### Adding Passwords 
1.After Launching the application there is an Floating Action Button on the bottom | right side.\
2.Click on that button to add new account.\
3.Enter Account Type Name,Username/Email and Password.\
4.Click on Add New Account to save the account.

 
 ### Viewing and Editing Passwords
1.After adding the new account user can immediately see the account on main screen.\
2.To edit the current Account details user can click on the particular account.\
3.After clicking user can see two options at the bottom -> edit or Delete.\
4.User cannot edit any details of account without clicking on edit button.\
5.After clicking on edit button user can edit the details and the edit button changes to save button to save the edited details.
 
 ### Deleting Passwords
1.To delete a particular Account click on that account.\
2.Click on delete option at bottom right corner.
3.It will open a dialog box , click delete.

## 4. Technical Details
 
 ### EncryptionAndDecryption
This project particularly use AES algorithm to encrypt and decrypt password and store it on local storage.\
There are three steps in this :-
1. Generate a key which is located at Android key Store.The Android Keystore is one of the safest options available for storing cryptographic keys on an Android device.
   To generate a key with the help of keystore.
2. For Encryption ->It initializes the cipher in encryption mode with a secret key obtained from getKey(). The encrypt() function takes a string input, encrypts it using the cipher, and returns the encrypted text concatenated with its initialization vector (IV),
 both encoded in Base64 format.
3. For Decryption ->A function, getDecryptCypherForIv(), which returns a decryption cipher initialized with a given initialization vector (IV) for decrypting data. The decrypt() function takes a string containing encrypted text and its corresponding IV, decodes them from Base64 format, decrypts the text using the decryption cipher with the provided IV, and returns the decrypted text as a string.

 ### Database
This project uses room database for storing Accounts/Passwords details in appropriate manner.\
There are four steps in this :-
1. Uses an Entity class called -> Account which should be annotated with Parcelize and extended with Parcelable so that we can send account info from one fragment to another fragment.
2. Uses Data Access Object(DAO) interface for that Entity Class to provide operations like insert,delete,update,and a query which give us all accounts contain inside database(Note -> all these functions should be "suspend" because task related to database should always use background thread.)
3. Uses an Database abstract class create a room database which annotated with @Database contains (entities,version,exportedSchema)
4. Uses a repository to connect database to DAO to insert accounts.
 
 ### User Interface
<p>
   <img src="https://github.com/chikuiui/Assignment/assets/97896257/3bcc2f6a-403a-4ced-b291-9913b5e6fb3c.png" alt="feed example" width = "200" >
   <img src="https://github.com/chikuiui/Assignment/assets/97896257/5bc823e2-5e14-4cb6-a625-a4c903575951.png" alt="feed example" width = "200" >
   <img src="https://github.com/chikuiui/Assignment/assets/97896257/c7bc4b4f-af11-4c2b-beb4-1f09850a01ee.png" alt="feed example" width = "200" >
   <img src="https://github.com/chikuiui/Assignment/assets/97896257/8b398c4b-316d-4e81-a06a-62e2eae9fed7.png" alt="feed example" width = "200" >
</p>

 ## Input Validation
If any of the fields are empty while editing or creating an account it will Toast a message saying -> "Fields are Empty" 
 
