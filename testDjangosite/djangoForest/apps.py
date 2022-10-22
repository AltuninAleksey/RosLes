from django.apps import AppConfig


class TestapiConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'djangoForest'

    class Meta:
        verbose_name = "Взаимодействие с БД"
