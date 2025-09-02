from fastapi import APIRouter

from api.endpoints_v1.endpoints import router as v1_router

api_router = APIRouter()

api_router.include_router(v1_router)