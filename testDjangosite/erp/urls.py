from django.urls import path
from erp import views


urlpatterns = [
    path('', views.index, name='index'),
    path('base', views.base),
    path('documents', views.documents),
    path('guide', views.guide),
    path('index11', views.index11),
    # path('guide/subjectRF', views.subjectRFview),
    path('guide/subjectRF', views.SubjectRFView.as_view(), name='SubjectRF'),
    path('guide/subjectRF/delete/<int:pk>', views.SubjectRFDelete.as_view(), name='delete_view'),
    path('guide/subjectRF/update/<int:pk>', views.SubjectRFUpdate.as_view(), name='update_subjectRF'),
    path('guide/post', views.PostView.as_view(), name="PostView"),
    path('guide/post/delete/<int:pk>', views.PostViewDelete.as_view(), name='delete_post'),
    path('guide/post/update/<int:pk>', views.PostViewUpdate.as_view(), name='update_post'),
    path('guide/branches', views.branches_view),
    path('guide/breeds', views.breeds_view),
    path('guide/district_forestly', views.district_forestly_view),
    path('guide/forestly', views.ForestlyView.as_view(), name="ForestlyView"),
    path('guide/gps', views.gps_view),
    path('guide/role', views.role_view),
    path('guide/type_of_reproduction', views.type_of_reproduction_view),
    path('guide/working_breeds', views.working_breeds_view)
]
