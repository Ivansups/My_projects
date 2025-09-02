from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.ext.asyncio import AsyncSession
from uuid import UUID
from typing import Annotated, List

from crud.crud import (
    get_users, get_works, create_user, create_work,
    get_user_by_id, get_work_by_id,
    update_user, update_work,
    delete_user, delete_work
)
from db.session import get_db
from schemas.user_schema import User, UserCreate, UserUpdate
from schemas.work_schema import Work, WorkCreate, WorkUpdate

router = APIRouter(prefix="/api/v1", tags=["api"])

# GET endpoints
@router.get("/users", response_model=List[User])
async def get_users_endpoint(db: AsyncSession = Depends(get_db)):
    """
    Получаем всех пользователей
    """
    return await get_users(db)

@router.get("/users/{user_id}", response_model=User)
async def get_user_by_id_endpoint(user_id: UUID, db: AsyncSession = Depends(get_db)):
    """
    Получаем пользователя по ID
    """
    user = await get_user_by_id(user_id, db)
    if not user:
        raise HTTPException(status_code=404, detail="Пользователь не найден")
    return user

@router.get("/works", response_model=List[Work])
async def get_works_endpoint(db: AsyncSession = Depends(get_db)):
    """
    Получаем все работы
    """
    return await get_works(db)

@router.get("/works/{work_id}", response_model=Work)
async def get_work_by_id_endpoint(work_id: UUID, db: AsyncSession = Depends(get_db)):
    """
    Получаем работу по ID
    """
    work = await get_work_by_id(work_id, db)
    if not work:
        raise HTTPException(status_code=404, detail="Работа не найдена")
    return work

# POST endpoints
@router.post("/users", response_model=User)
async def create_user_endpoint(user: UserCreate, db: AsyncSession = Depends(get_db)):
    """
    Создаем пользователя
    """
    return await create_user(user, db)

@router.post("/works", response_model=Work)
async def create_work_endpoint(work: WorkCreate, db: AsyncSession = Depends(get_db)):
    """
    Создаем работу
    """
    return await create_work(work, db)

# PUT endpoints
@router.put("/users/{user_id}", response_model=User)
async def update_user_endpoint(
    user_id: UUID, 
    user_data: UserUpdate, 
    db: AsyncSession = Depends(get_db)
):
    """
    Обновляем пользователя
    """
    user = await update_user(user_id, user_data, db)
    if not user:
        raise HTTPException(status_code=404, detail="Пользователь не найден")
    return user

@router.put("/works/{work_id}", response_model=Work)
async def update_work_endpoint(
    work_id: UUID, 
    work_data: WorkUpdate, 
    db: AsyncSession = Depends(get_db)
):
    """
    Обновляем работу
    """
    work = await update_work(work_id, work_data, db)
    if not work:
        raise HTTPException(status_code=404, detail="Работа не найдена")
    return work

# DELETE endpoints
@router.delete("/users/{user_id}")
async def delete_user_endpoint(user_id: UUID, db: AsyncSession = Depends(get_db)):
    """
    Удаляем пользователя
    """
    user = await delete_user(user_id, db)
    if not user:
        raise HTTPException(status_code=404, detail="Пользователь не найден")
    return {"message": "Пользователь успешно удален", "deleted_user": user}

@router.delete("/works/{work_id}")
async def delete_work_endpoint(work_id: UUID, db: AsyncSession = Depends(get_db)):
    """
    Удаляем работу
    """
    work = await delete_work(work_id, db)
    if not work:
        raise HTTPException(status_code=404, detail="Работа не найдена")
    return {"message": "Работа успешно удалена", "deleted_work": work}