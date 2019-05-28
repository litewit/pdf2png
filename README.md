# Pdf to Png Converter

It scans the root directory and if 
it finds any pdf file in the root directory 
it converts all pages of pdf to png image and 
store it in the folder with the name of the file name.

In order to run it run
```bash
gradle clean
gradle fatJar
```

This will create a jar file in build/libs folder

## Run
To run the jar file execute this command
```bash
java -jar pdf2png-1.0.0.jar
````

