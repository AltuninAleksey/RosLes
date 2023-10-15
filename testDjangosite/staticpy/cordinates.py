from PIL import Image, ImageDraw, ImageFont

image = Image.open("foto.jpg")
width1 = image.width
height1 = image.height
draw = ImageDraw.Draw(image)
font = ImageFont.truetype("OpenSans-Regular.ttf", size=22)
latitude = 53.21308675457966
longitude = 134.18286785318084
text = "Широта: " + format(latitude, '.4f') + "\nДолгота: " + format(longitude, '.4f')
new_width = (width1 - 220)
new_height = (height1 - 80)
draw.rectangle((new_width - 20, new_height -20, new_width + 220, new_height + 80), fill="black")
draw.text((new_width, new_height), text, font=font, fill="white")

image.show()
# image.save("long.jpg")



