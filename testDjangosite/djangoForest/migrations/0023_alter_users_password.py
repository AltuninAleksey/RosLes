# Generated by Django 4.1.3 on 2023-03-30 09:59

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0022_list_avg_height_undergrowth'),
    ]

    operations = [
        migrations.AlterField(
            model_name='users',
            name='password',
            field=models.CharField(max_length=300),
        ),
    ]
