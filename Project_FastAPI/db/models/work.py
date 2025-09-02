from sqlalchemy import Column, String, Boolean
from sqlalchemy.orm import relationship
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.sql.expression import text

from db.base_class import Base

class Work(Base):
    __tablename__ = "works"
    id = Column(UUID, primary_key=True, default=text("gen_random_uuid()"))
    name = Column(String, nullable=False)
    description = Column(String, nullable=False)
    status = Column(Boolean, nullable=False)

    users = relationship("User", secondary="user_works", back_populates="works")
