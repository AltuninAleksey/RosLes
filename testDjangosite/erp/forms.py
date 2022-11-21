from django import forms
from djangoForest.models import *


# class SubjectRfForm(forms.Form):
#     name_subject_RF = forms.CharField(label="Субъект РФ:", max_length=255)


class SubjectRfForm(forms.ModelForm):
    class Meta:
        model = SubjectRF
        fields = ['name_subject_RF']
        widgets = {
            'name_subject_RF': forms.TextInput(attrs={'placeholder': 'Наименование субъекта'}),
        }

#
class SubjectRFUpdateForm(forms.ModelForm):
    class Meta:
        model = SubjectRF
        fields = ['name_subject_RF']
        # widgets = {
        #     'name_subject_RF': forms.TextInput(attrs={'placeholder': 'Наименование субъекта'}),
        # }