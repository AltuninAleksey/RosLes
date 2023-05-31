import json

from django.urls import reverse

from rest_framework import status
from rest_framework.test import APITestCase


from djangoForest.models import *
from djangoForest.serializers import *

DEF_URL = "http://127.0.0.1:8000/"


class RegistrationTestCase(APITestCase):
    def test_registration(self):
        data = {"email": "test111@mail.ru",
                "password": "testpasss111",
                "FIO": "Fio Fio Fio",
                "phoneNumber": 89998887153}
        response = self.client.post(DEF_URL + "registration", data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_registration_char_number(self):
        data = {"email": "test111@mail.ru",
                "password": "testpasss111",
                "FIO": "Fio Fio Fio",
                "phoneNumber": "asd"}
        response = self.client.post(DEF_URL + "registration", data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data['error_text'], "Номер телефона должен состоять только из цифр.")

    def test_registration_none_number(self):
        data = {"email": "test111@mail.ru",
                "password": "testpasss111",
                "FIO": "Fio Fio Fio",
                "phoneNumber": ""}
        response = self.client.post(DEF_URL + "registration", data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data["error_text"], "Поле номер телефона не может быть пустым")

    def test_registration_digit_FIO(self):
        data = {"email": "test111@mail.ru",
                "password": "testpasss111",
                "FIO": "Fio11 Fio23 Fio23",
                "phoneNumber": 321234}
        response = self.client.post(DEF_URL + "registration", data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data["error_text"], "В ФИО не могут находиться цифры")

    def test_registration_none_FIO(self):
        data = {"email": "test111@mail.ru",
                "password": "testpasss111",
                "FIO": "",
                "phoneNumber": 321234}
        response = self.client.post(DEF_URL + "registration", data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data["error_text"], "Поле ФИО не может быть пустым")

    def test_registration_none_password(self):
        data = {"email": "test111@mail.ru",
                "password": "",
                "FIO": "Fio Fio Fio",
                "phoneNumber": 321234}
        response = self.client.post(DEF_URL + "registration", data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data["error_text"], "Поле password не может быть пустым")

    def test_registration_none_email(self):
        data = {"email": "",
                "password": "test1111",
                "FIO": "Fio Fio Fio",
                "phoneNumber": 321234}
        response = self.client.post(DEF_URL + "registration", data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data["error_text"], "Поле email не может быть пустым")


class UserAuthTestCase(APITestCase):

    def setUp(self):
        self.correct_user = Users.objects.create(email = "test_valid@mail.ru", password = make_password("testpass", "pbkdf2_sha256"))
        self.valid_user = {
            "email": "test_valid@mail.ru",
            "password":"testpass"
        }

        self.invalid_user_none_email = {
            "email": "",
            "password": "testpassword"
        }

        self.invalid_user_none_pass = {
            "email": "test_valid@mail.ru",
            "password": ""
        }

    def test_get_invalid_auth_none_pass(self):
        response = self.client.post(DEF_URL + "auth", data = json.dumps(self.invalid_user_none_pass), content_type='application/json')
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)

    def test_get_invalid_auth_none_email(self):
        response = self.client.post(DEF_URL + "auth", self.invalid_user_none_email,
                                    content_type='application/json')
        self.assertEqual(response.status_code, status.HTTP_401_UNAUTHORIZED)

    # def test_get_valid_auth(self):
    #     response = self.client.post(DEF_URL + "auth", self.valid_user, content_type="application/json")
    #     self.assertEqual(response.status_code, status.HTTP_200_OK)


class SubjectRFTestCase(APITestCase):

    def setUp(self):
        self.update_obj = SubjectRF.objects.create(name_subject_RF = 'test')

        self.valid_update = {
            "name_subject_RF": "testtest"
        }

        self.invalid_update = {
            "name_subject_RF": ""
        }


    def test_get(self):
        response = self.client.get(DEF_URL + "subjectRF")
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_post(self):
        data = {"name_subject_RF": "testname"}
        response  = self.client.post(DEF_URL + "subjectRF", data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_post_none_subject_RF(self):
        data = {"name_subject_RF": ""}
        response = self.client.post(DEF_URL + "subjectRF", data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data['error_text'], "Это поле не может быть пустым.")

    def test_update_subject_RF(self):
        response = self.client.put(DEF_URL + f"subjectRF/{self.update_obj.pk}", data=self.valid_update)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_update_invalid_subject_RF(self):
        response = self.client.put(DEF_URL + f"subjectRF/{self.update_obj.pk}", data=self.invalid_update)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)


class ForestlyTestCase(APITestCase):

    def setUp(self):
        SubjectRF_obj = SubjectRF.objects.create(name_subject_RF = "test")
        self.update_object = Forestly.objects.create(name_forestly = "testname111", id_subject_rf = SubjectRF_obj)
        self.valid_update = {
            "name_forestly": "qwet",
            "id_subject_rf_id": 11
        }

        self.invalid_update_none_name = {
            "name_forestly": "",
            "id_subject_rf_id": 11
        }

        self.invalid_update_none_id_subject = {
            "name_forestly": "zxcv",
            "id_subject_rf": "asd"
        }
    def test_get(self):
        response = self.client.get(DEF_URL + "forestly")
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_post_forestly(self):
        data = {"name_forestly": "testname111", "id_subject_rf_id": 16}
        response = self.client.post(DEF_URL + "forestly", data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_update_valid(self):
        response = self.client.put(DEF_URL + f"forestly/{self.update_object.pk}", data = self.valid_update)
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_invalid_update_none_name(self):
        response = self.client.put(DEF_URL + f"forestly/{self.update_object.pk}", data = self.invalid_update_none_name)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(response.data['error_text'], "Поле forestly не может быть пустым")

    def test_invalid_update_none_subject_rf(self):
        response = self.client.put(DEF_URL + f"forestly/{self.update_object.pk}", data=self.invalid_update_none_id_subject)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)


class DistrictForestlyTestCase(APITestCase):

    def test_get(self):
        response = self.client.get(DEF_URL + 'districtforestly')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_post_valid(self):
        response = self.client.post(DEF_URL + 'districtforestly', data={
            "name_district_forestly": "test_district",
            "id_forestly_id": 23
        })
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_post_invalid_name_district(self):
        response = self.client.post(DEF_URL + 'districtforestly', data={
            "name_district_forestly": "",
            "id_forestly_id": 23
        })
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_post_invalid_id_forestly(self):
        response = self.client.post(DEF_URL + 'districtforestly', data={
            "name_district_forestly": "",
            "id_forestly_id": 23
        })
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)


class QuarterTestCase(APITestCase):

    def test_get_quarter_all(self):
        response = self.client.get(DEF_URL + 'quarter')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_post_quarter_valid(self):
        response = self.client.post(DEF_URL + 'quarter', data={
            "quarter_name": "32",
            "id_district_forestly_id": 6
        })
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_post_quarter_invalid_quarter_name(self):
        response = self.client.post(DEF_URL + 'quarter', data={
            "quarter_name": "",
            "id_district_forestly": 3
        })
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_post_quarter_invalid_id_district_forestly(self):
        response = self.client.post(DEF_URL + 'quarter', data={
            "quarter_name": "32",
            "id_district_forestly": 'asd'
        })
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)


class ListRegionTestCase(APITestCase):
    pass