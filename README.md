Side Project - Wafer Image to Wafer Map Converter 
=======================================
## waferImage2waferMap
waferImage2wafermap repository converts the image of wafer semiconductor into the wafer map to be used in a factory line.
A wafer, also called a slice or substrate, is a thin slice of semiconductor material, such as a crystalline silicon, used in electronics for the fabrication of integrated circuits and in photovoltaics for conventional, wafer-based solar cells.(Wikipedia). 

## Requirements
- Accessing the wafer images
- This project is used in Eclipse IDE with JAVA programming language.
- Use SINF format for conversion method.
- Use image processing technique to read the image.

## Design

This project should read the image and convert it to the SINF format. Image processing technique should be designed and applied for conversion quality. 

### SINF Format 

```java
DEVICE:xxx    identification assigned by the originator
LOT:xxx       identification assigned by the originator
WAFER:xxx     identification assigned by the originator
FNLOC:180     wafer flat position (0=TOP,90=RIGHT,180=BOT 270=LEFT)
ROWCT:62      number of rows
COLCT:63      number of columns
BCEQU:01      List of Bin Codes that are a good die
REFPX:        x-coord of reference die (optional)
REFPY:        y-coord of reference die (optional)
DUTMS:mm      die units of measurement (mm or mil)
XDIES:2.945   step along X
YDIES:2.945   step along Y
```
### Image Contrast 

Contrast is the difference in luminance or color that makes an object (or its representation in an image or display) distinguishable. In visual perception of the real world, contrast is determined by the difference in the color and brightness of the object and other objects within the same field of view. (Wikipedia). Brightness and contrast factors are significant factors that could result in failing or passing the ASIC processor component from the image. these factors will be set in the configuration file. 

### Digital Filters
## Edge Detection Filter 
Edge detection filter defines a complete die mask and considers it as health die. Then it tries to find a matching chip die size by applying edge detection technique. Fatal die processor can be detected by detecting faulty edge less than a certain threshold and the algorithm will ignore the NOK die in the mass production in the factory line.  

## Noise removal Filter 
Noise removal filter tries to detect background noise from the image and remove it from the whole process.

## Crop Filter 
Crop filter detects the wafer boundary and crops the image to reduce the unnecessary data processing throughout the whole image processing. 

## Expected Result
The output file contains converted SINF format file to be read from CNC machine in the factory line. 


