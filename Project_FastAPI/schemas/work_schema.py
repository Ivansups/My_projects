from datetime import datetime
from uuid import UUID
from pydantic import BaseModel, Field
from typing import Optional

class Work(BaseModel):
    id: UUID
    name: str = Field(..., min_length=1, max_length=100)
    description: str = Field(..., min_length=1, max_length=100)
    status: bool = Field(default=False)
    
    class Config:
        from_attributes = True
class WorkCreate(BaseModel):
    name: str = Field(nullable = False, min_length=1, max_length=100)
    description: str = Field(nullable = False, min_length=1, max_length=100)
    status: bool = Field(default=False)

class WorkUpdate(BaseModel):
    name: Optional[str] = Field(None, min_length=1, max_length=100)
    description: Optional[str] = Field(None, min_length=1, max_length=100)
    status: Optional[bool] = Field(None)
