import face_recognition
import json
import numpy as n
import io
import base64

from PIL import Image
from flask import Flask, request

app = Flask(__name__)

def get_byte_image(image_path):
    img = Image.open(image_path, mode='r')
    img_byte_arr = io.BytesIO()
    img.save(img_byte_arr, format='PNG')
    encoded_img = base64.encodebytes(img_byte_arr.getvalue()).decode('ascii')
    return encoded_img



 if __name__ == "__main__":
     app.run(host='0.0.0.0', port=9000)  # , debug=True)