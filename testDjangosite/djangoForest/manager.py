from django.contrib.auth.models import AbstractBaseUser, BaseUserManager, PermissionsMixin
from django.db import models
from django.contrib.auth.hashers import make_password
class AccountManager(BaseUserManager):
    def create_user(self, email, password=None, **extra_fields):
        if not email:
            raise ValueError('Такой пользователь уже существует')

        user = self.model(email=email, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user

    # Переопределим создание superuser`a. Т.к. если мы будем создавать его через create_user
    # то получится что мы хэшируем пароль superuser`a два раза, т.к. по дефолту он хэшируется автоматически.
    def create_superuser(self, email, password=None, **extra_fields):
        return Users.objects.create(login=login, password=password, is_superuser=True)