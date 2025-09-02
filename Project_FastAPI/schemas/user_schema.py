from uuid import UUID
from pydantic import BaseModel, Field
from typing import Optional

class User(BaseModel):
    id: UUID
    email: str = Field(..., min_length=1, max_length=100)
    name: str = Field(..., min_length=1, max_length=100)
    
    class Config:
        from_attributes = True

class UserCreate(BaseModel):
    name: str = Field(..., min_length=1, max_length=100)
    email: str = Field(..., min_length=1, max_length=100)

class UserUpdate(BaseModel):
    name: Optional[str] = Field(None, min_length=1, max_length=100)
    email: Optional[str] = Field(None, min_length=1, max_length=100)
