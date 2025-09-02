from sqlalchemy import Column, String
from sqlalchemy.orm import relationship
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.sql.expression import text

from db.base_class import Base

class User(Base):
    __tablename__ = "users"
    id = Column(UUID, primary_key=True, default=text("gen_random_uuid()"))
    email = Column(String, nullable=False, unique=True)
    name = Column(String, nullable=False)

    works = relationship("Work", secondary="user_works", back_populates="users")
