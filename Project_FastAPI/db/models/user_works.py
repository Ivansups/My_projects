from sqlalchemy import Column, ForeignKey, String, Boolean
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.sql.expression import text

from db.base_class import Base



class UserWorks(Base):
    __tablename__ = "user_works"

    id = Column(UUID, primary_key=True, default=text("gen_random_uuid()"))
    user_id = Column(UUID, ForeignKey("users.id"), nullable=False)
    work_id = Column(UUID, ForeignKey("works.id"), nullable=False)
    role = Column(String, nullable=True)
    status = Column(Boolean, default=True)
