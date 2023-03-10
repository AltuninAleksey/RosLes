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
    path('guide/branches', views.BranchesView.as_view(), name='BranchesView'),
    path('guide/branches/update/<int:pk>', views.BranchesUpdate.as_view(), name='update_branches'),
    path('guide/branches/delete/<int:pk>', views.BranchesDelete.as_view(), name='delete_branches'),
    path('guide/breeds', views.breeds_view),
    path('guide/district_forestly', views.DistrictForestlyView.as_view(), name='DistrictView'),
    path('guide/district_forestly/update/<int:pk>', views.DistrictForestlyUpdateView.as_view(), name='update_district'),
    path('guide/district_forestly/delete/<int:pk>', views.DistrictForestlyDelete.as_view(), name='delete_district'),
    path('guide/forestly', views.ForestlyView.as_view(), name="ForestlyView"),
    path('guide/forestly/delete/<int:pk>', views.ForestlyViewDelete.as_view(), name="delete_forestly" ),
    path('guide/forestly/update/<int:pk>', views.ForestlyUpdateView.as_view(), name="update_forestly"),
    path('guide/gps', views.GpsView.as_view(), name='GPSView'),
    # path('guide/gps/update/<int:pk>', views.GpsUpdate.as_view(), name='update_gps'),
    # path('guide/gps/delete/<int:pk>', views.GpsDelete.as_view(), name='delete_gps'),
    path('guide/role', views.RoleView.as_view(), name='RoleView'),
    path('guide/role/update/<int:pk>', views.RoleUpdate.as_view(), name='update_role'),
    path('guide/role/delete/<int:pk>', views.RoleDelete.as_view(), name='delete_role'),
    path('guide/type_of_reproduction', views.type_of_reproduction_view),
    path('guide/working_breeds', views.working_breeds_view),
    path('documents/listregion', views.ListRegionView.as_view(), name='ListRegionView'),
    path('documents/sample', views.SampleView.as_view()),
    #MPO 01.12.22: Add new url for page "recalculationOnTrialArea" and "recalculationOnTrialAreaDetail"
    path('documents/recalculationOnTrialArea', views.getRecalculationOnTrialArea),
    path('documents/recalculationOnTrialAreaDetail', views.getRecalculationOnTrialAreaDetail),
    path('documents/recalculationOnTrialAreaDetailNew', views.getRecalculationOnTrialAreaDetailNew),
    #MPO 21.01.23: Add new url
    path('documents/listRegions', views.getListRegions),
    path('documents/statementRecalculations', views.getStatementRecalculations),
]

