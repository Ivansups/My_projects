from fastapi import FastAPI, Depends

from api.api_router import api_router
from db.session import engine, get_db
from db.base_class import Base


app = FastAPI(debug=True)

@app.on_event("startup")
async def startup_event():
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)


app.include_router(api_router)