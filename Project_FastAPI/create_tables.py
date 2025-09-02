from db.base_class import Base
from db.models.user import User
from db.models.work import Work
from db.models.user_works import UserWorks
from db.session import engine

def create_tables():
    """Создает все таблицы в базе данных"""
    print("Создаю таблицы...")
    Base.metadata.create_all(bind=engine)
    print("Таблицы созданы успешно!")

if __name__ == "__main__":
    create_tables()
