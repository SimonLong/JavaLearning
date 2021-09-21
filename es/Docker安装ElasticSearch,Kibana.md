### Docker安装ElasticSearch

1. 拉取ElasticSearch镜像

   ```
   docker pull elasticsearch:7.14.1
   ```

   

2. 创建数据挂载文件与目录

   ```
   mkdir -p /mydata/elasticsearch/config
   mkdir -p /mydata/elasticsearch/data
   mkdir -p /mydata/elasticsearch/plugins
   echo "http.host: 0.0.0.0" >> /mydata/elasticsearch/config/elasticsearch.yml
   ```

   

3. 创建容器并启动

   ```
   docker run --name elasticsearch -p 9200:9200 -p 9300:9300  -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx128m" -v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /mydata/elasticsearch/es/data:/usr/share/elasticsearch/data -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins -d elasticsearch:7.14.1
   ```

   

### Docker安装Kibana

1. 拉取镜像

   ```
   docker pull kibana:7.14.1
   ```

   

2. 运行容器

   ```
   docker run --name kibana -e ELASTICSEARCH_HOSTS=http://192.168.3.6:9200 -p 5601:5601 -d kibana:7.14.1
   ```

3. 访问地址

   ```
   http://localhost:5601/app/kibana
   ```

   