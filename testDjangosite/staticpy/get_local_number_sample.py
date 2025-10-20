from django.db.models import Max
# from djangoForest.serializers import *
from django.db.models import Q



def get_local_number_sample(id_list_region: int) -> int:
    from djangoForest.models import ListRegion, Sample, Profile
    cur_number_sample = {'number_sample__max': 0}
    all_samples = Sample.objects.filter(id_list_region = id_list_region)
    cur_number_sample = all_samples.aggregate(Max('number_sample'))
    if cur_number_sample['number_sample__max'] is None:
        cur_number_sample['number_sample__max'] = 0

    return cur_number_sample

