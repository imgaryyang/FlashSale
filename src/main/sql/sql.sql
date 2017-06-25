-- ������Ŀ�����ݿ�ű�
-- ��ʼ����һ�����ݿ�
CREATE DATABASE flashsale;
-- ʹ�����ݿ�
USE flashsale;
-- ������ɱ����
CREATE TABLE product(
  `product_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���ID',
  `name` VARCHAR(120) NOT NULL COMMENT '��Ʒ����',
  `stock` INT NOT NULL COMMENT '�������',
  `start_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '��ɱ������ʱ��',
  `end_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '��ɱ������ʱ��',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '������ʱ��',
  PRIMARY KEY (product_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE =InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='��ɱ����';

-- �����ʼ������

insert into
  product(name,stock,start_time,end_time)
values
  ('1000Ԫ��ɱIPhone7',100,'2017-6-22 00:00:00','2017-6-22 00:00:00'),
  ('500Ԫ��ɱī������Ʊ',200,'2017-6-22 00:00:00','2017-6-22 00:00:00'),
  ('300Ԫ��ɱһ���ӰƱ',300,'2017-6-22 00:00:00','2017-6-22 00:00:00'),
  ('100Ԫ��Iphone100',400,'2017-6-22 00:00:00','2017-6-22 00:00:00');

-- ��ɱ�ɹ���ϸ��
-- �û���¼�����Ϣ
create table saled_order(
  `product_id` BIGINT NOT NULL COMMENT '��ɱ��ƷID',
  `user_phone` BIGINT NOT NULL COMMENT '�û��ֻ���',
  `status` TINYINT NOT NULL DEFAULT -1 COMMENT '״̬��ʾ:-1��Ч 0�ɹ� 1�Ѹ���',
  `create_time` TIMESTAMP NOT NULL COMMENT '����ʱ��',
  PRIMARY KEY (product_id,user_phone), /*��������*/
  KEY idx_create_time(create_time)
)ENGINE =InnoDB DEFAULT CHARSET =utf8 COMMENT ='��ɱ�ɹ���ϸ��';