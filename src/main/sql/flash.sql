-- ��ɱ�洢����
DELIMITER $$ -- �ֺŻ���ת��Ϊ$$
-- ����洢����
-- ����: in��ʾ�������;out ��ʾ������� �ɸ�ֵ
-- row_count() ������һ���޸�����SQL��Ӱ������
-- row_count() 0:δ�޸����� ;>0 �޸ĵ�����;<0 SQL����/δִ���޸�SQL
CREATE PROCEDURE `flashsale`.`execute_sale`
(in v_flashsale_id bigint, in v_phone bigint,
in v_sale_time timestamp, out r_result int)
BEGIN
	DECLARE insert_count int DEFAULT 0;
	START TRANSACTION;
	insert ignore into order_ 
		(product_id,user_phone,create_time)
		values (v_flashsale_id,v_phone,v_sale_time);
	select row_count() into insert_count;
	IF (insert_count = 0) THEN
		ROLLBACK;
		set r_result = -1;
	ELSEIF(insert_count < 0) THEN
		set r_result = -2;
	ELSE
		update product
		set stock = stock - 1
		where product_id = product_id
		and end_time > v_sale_time
		and start_time < v_sale_time
		and stock > 0;
		select row_count() into insert_count;
		IF (insert_count = 0) THEN
		ROLLBACK;
		set r_result = 0;
		ELSEIF (insert_count < 0) THEN
			ROLLBACK;
			set r_result = -2;
			ELSE
				COMMIT;
				set r_result = 1;
		END IF;
	END IF;
END;
$$
--����洢���̽���
 DELIMITER ;
 set @r_result = -3;
 --ִ�д洢����
 call execute_sale(1003,13917556899,now(),@r_result);
 -- ��ȡ���
 select @r_result;
 