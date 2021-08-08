insert into author(id, name)
values ('d74684e8-7812-41bc-85c3-70d0e4931f69', 'Пушкин'),
       ('637a373a-3fb8-419b-bb80-7e347272e973', 'Лола Федорович'),
       ('0dd0b194-b7df-43ef-9d4b-dd6de2f7f8e0', 'Антон'),
       ('b279d4e7-b465-4f71-bdda-b802e25b9b6a', 'Кирилл Кириллов Антонович');

insert into book(id, title, author_id)
values ('5a23c227-c399-447c-a936-bc1fbb408f60', 'Онегин', 'd74684e8-7812-41bc-85c3-70d0e4931f69'),
       ('eb09a9e4-fdd9-462f-9bac-14b693c63eb1', 'Привет с моря', '637a373a-3fb8-419b-bb80-7e347272e973'),
       ('d9711d1c-4961-4542-8655-a78b949a6116', 'Как дела на даче', 'd74684e8-7812-41bc-85c3-70d0e4931f69'),
       ('35b24865-3c7d-4c3b-9748-627d264213c0', 'Война и мир новая адаптация', '637a373a-3fb8-419b-bb80-7e347272e973');
