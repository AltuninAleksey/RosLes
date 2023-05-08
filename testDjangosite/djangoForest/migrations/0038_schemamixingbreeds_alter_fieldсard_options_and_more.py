# Generated by Django 4.1.3 on 2023-04-25 12:40

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('djangoForest', '0037_fieldсard'),
    ]

    operations = [
        migrations.CreateModel(
            name='SchemaMixingBreeds',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name_schema', models.CharField(max_length=300)),
            ],
        ),
        migrations.AlterModelOptions(
            name='fieldсard',
            options={'verbose_name': 'Полевая карточка', 'verbose_name_plural': 'Полевая карточка'},
        ),
        migrations.RemoveField(
            model_name='descriptionregion',
            name='schema_mixing_breeds',
        ),
        migrations.AddField(
            model_name='descriptionregion',
            name='id_schema_mixing_breeds',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, to='djangoForest.schemamixingbreeds', verbose_name='Схема смешения пород'),
        ),
    ]
