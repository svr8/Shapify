# Shapify
Shapify is a library to ease the process of converting real-world imagery into digital format. The aim is to help digital artist so can extract pieces of art from images they've found somewhere and avoid process of duplicating it by hand.
The library does not aims to offer tools for novice developers so the artists can continue their work without having to learn heavy coding skills.

# Examples


<img src="https://github.com/svr8/Shapify/blob/master/images/image.jpeg" alt="drawing" width="400" height="400"/> 
<img src="https://github.com/svr8/Shapify/blob/master/images/shapified2.jpeg" alt="drawing" width="400" height="400"/> <img src="https://github.com/svr8/Shapify/blob/master/images/shapified.jpeg" alt="drawing" width="400" height="400"/>

Here, every shape identified is given a unique number(index). 
A particular shape can be fetched by using:
```
ImageShaper shaper = new ImageShaper(sourceImage);
Shape shape = shaper.getShape(3);
```
