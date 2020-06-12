from flask import Flask, request
from flask_cors import CORS
import os
import face_recognition
import base64
from io import BytesIO
from PIL import Image
import os
app = Flask(__name__)
CORS(app)

SAVED_IMAGES = os.environ.get("PICTURE_PATH")
app.config['SAVED_IMAGES'] = SAVED_IMAGES

@app.route("/image", methods=['POST', 'GET'])
def search():
    data = request.get_json()
    personalPicture = data['image']
    starter = personalPicture.find(',')
    image_data = personalPicture[starter + 1:]
    im = Image.open(BytesIO(base64.b64decode(image_data)))
    im.save("imageToSearch.png")
    print(data)
    return compare()


def compare():
    #threshold = 0.5
    images = os.listdir(SAVED_IMAGES)
    image_to_be_matched = face_recognition.load_image_file("imageToSearch.png")
    image_to_be_matched_encoded = face_recognition.face_encodings(image_to_be_matched)[0]
    for image in images:
        current_image = face_recognition.load_image_file(SAVED_IMAGES + image)
        current_image_encoded = face_recognition.face_encodings(current_image)[0]
        face_distance = face_recognition.face_distance([image_to_be_matched_encoded], current_image_encoded)
        results = face_recognition.compare_faces([current_image_encoded], image_to_be_matched_encoded, 0.3)
     #   if face_distance < threshold:
        if results[0]:
            print("matched " + image)
            image_ext = os.path.splitext(image)[0]
            return {'image_name': image_ext}

    print("no match")
    return {"image_name": "No Match",}


#@app.route("/createUser", methods=['POST', 'GET'])
def save():
    data = request.get_json()
    id = data['number']
    file = data['personalPicture']
    starter = file.find(',')
    image_data = file[starter + 1:]
    im = Image.open(BytesIO(base64.b64decode(image_data)))
    im.save(SAVED_IMAGES + id + '.png')
    return "SAVED"



if __name__ == '__main__':
    app.run(debug=True, port= 8000)