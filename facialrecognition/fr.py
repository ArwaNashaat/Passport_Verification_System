# import the libraries
import os
import face_recognition


# make a list of all the available images
images = os.listdir('celebrities')

# load your image
image_to_be_matched = face_recognition.load_image_file('unknown.jpg')

# encoded the loaded image into a feature vector
image_to_be_matched_encoded = face_recognition.face_encodings(
    image_to_be_matched)[0]

# iterate over each image
for image in images:
    current_image = face_recognition.load_image_file("celebrities/" + image)
    current_image_encoded = face_recognition.face_encodings(current_image)[0]
    result = face_recognition.compare_faces(
        [image_to_be_matched_encoded], current_image_encoded)
    if result[0] == True:
        print ("Matched: " + image)
    else:
        print ("Not matched: " + image)