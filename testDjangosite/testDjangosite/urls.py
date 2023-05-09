"""testDjangosite URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include, re_path
from djangoForest.views import *
from django.views.static import serve
from django.conf.urls.static import static

from testDjangosite import settings

urlpatterns = [
    re_path(r'^media/(?P<path>.*)$', serve,{'document_root': settings.MEDIA_ROOT}),
    re_path(r'^static/(?P<path>.*)$', serve,{'document_root': settings.STATIC_ROOT}),
    path('getsamplefromlistregion', GetSampleFromListRegionId.as_view()),
    path('responsesqlite', SendResponseSQLite.as_view()),
    path('admin/', admin.site.urls),
    path('undergrowth', UndergrowthView.as_view()),
    path('undergrowth/<int:pk>', UndergrowthView.as_view()),
    path('schemamixing', SchemaMixingBreedsView.as_view()),
    path('schemamixing/<int:pk>', SchemaMixingBreedsView.as_view()),
    path('point7table', Point7TableView.as_view()),
    path('point7table/<int:pk>', Point7TableView.as_view()),
    path('point7sapling', Point7TableSaplingView.as_view()),
    path('point7sapling/<int:pk>', Point7TableSaplingView.as_view()),
    path('purposeforest', PurposeOfForestsView.as_view()),
    path('purposeforest/<int:pk>', PurposeOfForestsView.as_view()),
    path('forestprotectioncategory', ForestProtectionCategoryView.as_view()),
    path('forestprotectioncategory/<int:pk>', ForestProtectionCategoryView.as_view()),
    path('typeforestgrowingconditions', TypeForestGrowingConditionsView.as_view()),
    path('typeforestgrowingconditions/<int:pk>', TypeForestGrowingConditionsView.as_view()),
    path('fundlands', CategoryOfForestFundLandsView.as_view()),
    path('fundlands/<int:pk>', CategoryOfForestFundLandsView.as_view()),
    path('methodofreforestation', MethodOfReforestationView.as_view()),
    path('methodofreforestation/<int:pk>', MethodOfReforestationView.as_view()),
    path('economy', EconomyView.as_view()),
    path('economy/<int:pk>', EconomyView.as_view()),
    path('undergrowthbydefault', UndergrowthByDefaultView.as_view()),
    path('', include('djangoForest.urls')),
    path('getbase', AnroidDownland.as_view()),
    path('descriptionregion', GetAllDescriptionRegion.as_view()),
    path('descriptionregion/<int:pk>', GetAllDescriptionRegion.as_view()),
    path('descriptionregionfilter', DescriptionRegionFilter.as_view()),
    path('fieldcard', GetFieldCard.as_view()),
    path('fieldcard/<int:pk>', GetFieldCard.as_view()),
    path('fieldcardfilter', FieldCardFilter.as_view()),
    # re_path(r'upload/(?P<filename>[^/]+)$', PhotoPointView.as_view()),
    path('listregionfilters', ListRegionFilters.as_view()),
    path('getallequallistregion', GetAllEqualListRegion.as_view()),
    path('upload', PhotoPointView.as_view()),
    path('upload/<int:pk>', PhotoPointView.as_view()),
    path('auth', UserAuth.as_view()),
    path('registration', UserRegistration.as_view()),
    path('sampleandother', CreateSampleAndOther.as_view()),
    path('api/v1/gettable', TestAPIView.as_view()),
    path('allforest', AllForestlyViewSet.as_view({'get': 'list'})),
    path('getlistdata/<int:pk>', GetDocumentListData.as_view({'get': 'list'})),
    path('getsampledata/<int:pk>', GetSampleListData.as_view({'get': 'list'})),
    path('getallsampledata', GetAllSampleListData.as_view({'get': 'list'})),
    path('getforestlybysubjectid/<int:pk>', GetForestlyBySubjectRFId.as_view({'get': 'list'})),
    path('getdistrictbyforestly/<int:pk>', GetDistrictForestlyByForestlyId.as_view({'get':'list'})),
    path('getquarterbydistrictid/<int:pk>', GetQuarterByDistrictId.as_view({'get':'list'})),
    path('getalllistregion', GetAllListRegionData.as_view({'get':'list'})),
    path('unionlistregion', UnionListRegions.as_view()),
    path('subjectRF', SubjectRFview.as_view()),
    path('subjectRF/<int:pk>', SubjectRFview.as_view()),
    path('profile', ProfileView.as_view()),
    path('profile/<int:pk>', ProfileView.as_view()),
    path('list', ListView.as_view()),
    path('list/<int:pk>', ListView.as_view()),
    path('gps', GpsView.as_view()),
    path('gps/<int:pk>', GpsView.as_view()),
    path('gpsbylistregion/<int:id_list_region>', GpsBySampleView.as_view()),
    path('listregion', ListRegionView.as_view()),
    path('listregion/<int:pk>', ListRegionView.as_view()),
    path('sample', SampleView.as_view()),
    path('sample/<int:pk>', SampleView.as_view()),
    path('post', PostView.as_view()),
    path('post/<int:pk>', PostView.as_view()),
    path('workingbreed', WorkingBreedView.as_view()),
    path('workingbreed/<int:pk>', WorkingBreedView.as_view()),
    path('role', RoleView.as_view()),
    path('role/<int:pk>', RoleView.as_view()),
    path('reproduction', ReproductionView.as_view()),
    path('reproduction/<int:pk>', ReproductionView.as_view()),
    path('forestly', ForestlyView.as_view()),
    path('forestly/<int:pk>', ForestlyView.as_view()),
    path('districtforestly', DistrictForestlyView.as_view()),
    path('districtforestly/<int:pk>', DistrictForestlyView.as_view()),
    path('breed', BreedView.as_view()),
    path('breed/<int:pk>', BreedView.as_view()),
    path('branches', BranchesView.as_view()),
    path('branches/<int:pk>', BranchesView.as_view()),
    path('quarter', QuarterView.as_view()),
    path('quarter/<int:pk>', QuarterView.as_view()),
    path('erp/', include('erp.urls')),
    path('general_docs/', include('general_docs.urls')),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)