from django.db.models import Max
# from djangoForest.serializers import *
from django.db.models import Q



def get_local_id_czl(id_profile: int) -> int:
    from djangoForest.models import Profile, CZL, ListRegion, SubjectRF
    lst_czl = []
    id_subject = Profile.objects.filter(id = id_profile).values('id_user_id__subject_rf_id')[0]
    if id_subject['id_user_id__subject_rf_id'] == 27:
        lst_region = ListRegion.objects.filter(id_profile_id__id_user_id__subject_rf_id=27)
    else:
        if CZL.objects.filter(id_main_subject = id_subject['id_user_id__subject_rf_id']).exists():
            main_czl = CZL.objects.filter(id_main_subject = id_subject['id_user_id__subject_rf_id']).values('id_main_subject')[0]
            list_czl = CZL.objects.filter(id_main_subject=main_czl['id_main_subject']).values('id_subject_id')
            # list_czl.append(main_czl['id_main_subject'])
        elif CZL.objects.filter(id_subject_id = id_subject['id_user_id__subject_rf_id']).exists():
            main_czl = CZL.objects.filter(id_subject_id = id_subject['id_user_id__subject_rf_id']).values('id_main_subject')[0]
            list_czl = CZL.objects.filter(id_main_subject = main_czl['id_main_subject']).values('id_subject_id')
            # list_czl.append(main_czl[0])
        lst_subject = SubjectRF.objects.filter(Q(id__in = list_czl) | Q(id = main_czl['id_main_subject']))
        lst_region = ListRegion.objects.filter(id_profile_id__id_user_id__subject_rf_id__in = lst_subject)
    id_czl = lst_region.aggregate(Max('number_region'))
    if id_czl['number_region__max'] is None:
        id_czl['number_region__max'] = 0
    return id_czl