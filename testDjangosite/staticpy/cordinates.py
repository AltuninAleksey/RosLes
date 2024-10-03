from PIL import Image, ImageDraw, ImageFont
from testDjangosite.settings import BASE_DIR
import os
import time

def coord(_latitude: str, _longitude: str, path: str) ->None:
    # new_path = str(BASE_DIR)+'/'+path
    new_path = path
    print(new_path)
    image = Image.open(new_path)
    width1 = image.width
    height1 = image.height
    draw = ImageDraw.Draw(image)
    # font = ImageFont.truetype("Pillow/Tests/fonts/ArefRuqaa-Regular.ttf", size=22)
    font = ImageFont.load_default()
    latitude = float(_latitude)
    longitude = float(_longitude)
    Secs = os.path.getctime(new_path)
    Local = time.localtime(Secs)
    Str = time.strftime("%d.%m.%Y %H:%M", Local)
    text = f"{Str}\n{latitude:.4f}, {longitude:.4f}"
    new_width = (width1 - 215)
    new_height = (height1 - 75)
    draw.text((new_width, new_height, new_width, new_height), text, font=font, fill="white")
    image.show()
    image.save(path)
    # return image

