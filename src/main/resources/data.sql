select * from item;
select * from category;
select * from orders;
select * from bill;
insert into item (item_id, quantity, category_name, item_name, price, tax) values(2,21,'Drinks','Maaza',23.0,2.0);
insert into orders(item_id,order_id,amount,quantity) values(2,10,46.0,2);
insert into orders(item_id,order_id,amount,quantity) values(2,1,46.0,2);
insert into bill(order_id,purchase_date) values(10,'2020/03/10 15:53:07');
