from calendar import c
from uuid import UUID
from pydantic import BaseModel, Field
from typing import Optional


class UserWorks(BaseModel):
    id: UUID
    user_id: UUID
    work_id: UUID
    role: Optional[str] = Field(None, min_length=1, max_length=100)
    status: bool = Field(default=True)
    
    class Config:
        from_attributes = True

class UserWorksCreate(BaseModel):
    user_id: UUID
    work_id: UUID
    role: Optional[str] = Field(None, min_length=1, max_length=100)
    status: bool = Field(default=True)

class UserWorksUpdate(BaseModel):
    user_id: Optional[UUID] = Field(None)
    work_id: Optional[UUID] = Field(None)
    role: Optional[str] = Field(None, min_length=1, max_length=100)
    status: Optional[bool] = Field(None)