Side Project - Wafer Image to Wafer Map Converter 
=======================================
# waferImage2waferMap
waferImage2wafermap repository converts the image of wafer semiconductor into the wafer map to be used in factory line.
A wafer, also called a slice or substrate, is a thin slice of semiconductor material, such as a crystalline silicon, used in electronics for the fabrication of integrated circuits and in photovoltaics for conventional, wafer-based solar cells.(WikiPedia). 

## Requirements
- Accesing to the wafare images
- This project is used in Eclipse IDE with JAVA programming language.
- Use SINF format for conversion
- Use Image processing technique to read the image

## Design

This project should read the image and convert it to the SINF format. Image processing technique should be designed and applied for conversion quality. 

```java
DEVICE:xxx    identification assigned by originator
LOT:xxx       identification assigned by originator
WAFER:xxx     identification assigned by originator
FNLOC:180     wafer flat position (0=TOP,90=RIGHT,180=BOT 270=LEFT)
ROWCT:62      number of rows
COLCT:63      number of columns
BCEQU:01      List of Bin Codes that are good die
REFPX:        x-coord of reference die (optional)
REFPY:        y-coord of reference die (optional)
DUTMS:mm      die units of measurement (mm or mil)
XDIES:2.945   step along X
YDIES:2.945   step along Y
```

## Expected Result



