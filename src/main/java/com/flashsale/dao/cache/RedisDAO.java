package com.flashsale.dao.cache;

import org.apache.log4j.Logger;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.flashsale.entity.Product;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDAO {
	private final Logger log = Logger.getLogger(this.getClass());
	private JedisPool jedisPool;
	private RuntimeSchema<Product> schema = 
			RuntimeSchema.createFrom(Product.class);

	public RedisDAO(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}

	public Product getProduct(long productId) {
		// redis�����߼� һ����ά��:��ʱά��
		
		try (Jedis jedis = jedisPool.getResource();) {

			String key = "product:" + productId;
			// ��û��ʵ���ڲ����л�����
			// �����Զ���ķ�ʽ�����л�
			byte[] bytes = jedis.get(key.getBytes());
			if (bytes != null){
				//�ն���
				Product product = schema.newMessage();
				ProtostuffIOUtil.mergeFrom(bytes, product, schema);
				return product;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;

	}

	public String putProduct(Product product) {
		//���л�����
		try (Jedis jedis = jedisPool.getResource();) {

			String key = "product:" + product.getProductId();
			byte[] bytes = ProtostuffIOUtil.toByteArray(product,
					schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			int timeout = 60 * 60;
			String result = jedis.setex(key.getBytes(), timeout, bytes);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
