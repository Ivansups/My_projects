from uuid import UUID
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select

from db.models.user import User
from db.models.work import Work
from schemas.user_schema import UserCreate, UserUpdate
from schemas.work_schema import WorkCreate, WorkUpdate

async def get_users(db: AsyncSession):  # Функция получения всех пользователей
    result = await db.execute(select(User))
    return result.scalars().all()

async def get_works(db: AsyncSession):  # Функция получения всех работ
    result = await db.execute(select(Work))
    return result.scalars().all()

async def get_user_by_id(user_id: UUID, db: AsyncSession):  # Функция получения пользователя по id
    result = await db.execute(select(User).where(User.id == user_id))
    return result.scalar_one_or_none()

async def get_work_by_id(work_id: UUID, db: AsyncSession):  # Функция получения работы по id
    result = await db.execute(select(Work).where(Work.id == work_id))
    return result.scalar_one_or_none()

async def create_user(user_data: UserCreate, db: AsyncSession):  # Функция создания пользователя
    user = User(**user_data.dict())
    db.add(user)
    await db.commit()
    await db.refresh(user)
    return user

async def create_work(work_data: WorkCreate, db: AsyncSession):  # Функция создания работы
    work = Work(**work_data.dict())
    db.add(work)
    await db.commit()
    await db.refresh(work)
    return work

async def update_user(user_id: UUID, user_data: UserUpdate, db: AsyncSession):  # Функция обновления пользователя
    user = await get_user_by_id(user_id, db)
    if user:
        # Обновляем только переданные поля
        if user_data.name is not None:
            user.name = user_data.name
        if user_data.email is not None:
            user.email = user_data.email
        await db.commit()
        await db.refresh(user)
        return user
    return None

async def update_work(work_id: UUID, work_data: WorkUpdate, db: AsyncSession):  # Функция обновления работы
    work = await get_work_by_id(work_id, db)
    if work:
        # Обновляем только переданные поля
        if work_data.name is not None:
            work.name = work_data.name
        if work_data.description is not None:
            work.description = work_data.description
        if work_data.status is not None:
            work.status = work_data.status
        await db.commit()
        await db.refresh(work)
        return work
    return None

async def delete_user(user_id: UUID, db: AsyncSession):  # Функция удаления пользователя
    user = await get_user_by_id(user_id, db)
    if user:
        await db.delete(user)
        await db.commit()
        return user
    return None

async def delete_work(work_id: UUID, db: AsyncSession):  # Функция удаления работы
    work = await get_work_by_id(work_id, db)
    if work:
        await db.delete(work)
        await db.commit()
        return work
    return None