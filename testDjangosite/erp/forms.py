from django import forms
from djangoForest.models import *

class PostForm(forms.ModelForm):
    class Meta:
        model = Post
        fields = ['post_name']
        widgets = {
            'post_name': forms.TextInput(attrs={'placeholder': 'Наименование должности'}),
        }


class PostUpdateForm(forms.ModelForm):
    class Meta:
        model = Post
        fields = ['post_name']
        widgets = {
            'post_name': forms.TextInput()
        }


# Форма для вывода
class SubjectRfForm(forms.ModelForm):
    class Meta:
        model = SubjectRF
        fields = ['name_subject_RF']
        widgets = {
            'name_subject_RF': forms.TextInput(attrs={'placeholder': 'Наименование субъекта'}),
        }


class SubjectRFUpdateForm(forms.ModelForm):
    class Meta:
        model = SubjectRF
        fields = ['id', 'name_subject_RF']
        widgets = {
            'id': forms.TextInput(),
            'name_subject_RF': forms.TextInput(),
        }


class ForestlyForm(forms.ModelForm):
    class Meta:
        model = Forestly
        fields = ['name_forestly', 'id_subject_rf']
        # queryset_rf = SubjectRF.objects.all()
        id_subject_rf = forms.ModelChoiceField(queryset=SubjectRF.objects.all(), empty_label=None, to_field_name='subjectrf'),
        widgets = {
            'name_forestly': forms.TextInput(attrs={'placeholder': 'Лесничество'}),
        }