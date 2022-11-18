from django import forms


class SubjectRfForm(forms.Form):
    name_subject_RF = forms.CharField(label="Субъект РФ:", max_length=255)