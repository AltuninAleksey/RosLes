from .models import CheckTrigger

def signals():
    queryset = CheckTrigger.objects.all()
    queryset.bool = 1
    queryset.save()
