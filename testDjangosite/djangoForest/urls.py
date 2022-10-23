from rest_framework import routers
from .views import ForestViewSet


router = routers.DefaultRouter()
router.register('api/admin', ForestViewSet, 'admin')

urlpatterns = router.urls