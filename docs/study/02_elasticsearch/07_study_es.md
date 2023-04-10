# Elasticsearch Study.07

## 인덱스 설정과 매핑 - Settings & Mappings

`Elasticsearch` 의 `Index` **인덱스**는 도큐먼트 데이터들의 논리적인 집합이다. 
인덱스는 하나의 노드에 저장되기 보다는 [03. Elasticsearch 시스템구조](./03_study_es.md) 에서 다룬 내용처럼
여러 샤드로 분산되어 데이터 저장하고, 데이터 무결성의 보장과 검색의 최적화 기능을 제공한다.
그리고 `Analyzer` **애널라이저**와 같은 과정을 거쳐 분리된 데이터도 인덱스 단위로 저장된다.

`Elasticsearch` 의 핵심인 **인덱스** 단위에서의 설정과 데이터 명세인 **인덱스 매핑**에 대해 학습이 필요할 것이다.

> 설정을 간단하게 파악해둬야 장애 대응이 가능하다.

---

## Settings

### `_settings`

- `_settings` API 를 사용하면 해당 인덱스의 설정 정보를 조회할 수 있다.
- 인덱스의 설정을 보기 위해서는 다음과 같이 조회할 수도 있지만, `_settings` 를 생략할 수도 있다.

```json lines
GET my_index/_settings
```

> `_settings` 를 생략할 경우, `_settings`, `_mappings` 정보 모두 확인 가능하다.

### `number_of_shards`

- 인덱스의 `Shard` **샤드**의 수를 정하는 설정이다.
- 해당 설정을 지정하지 않는 경우 `7,0 버전`부터는 기본적으로 1개를 생성하고, `6.X 버전`까지는 기본 5개가 설정된다.
- `number_of_shards` 설정 정보는 최초에 인덱스 생성 이후 변경이 불가능하다. 변경하고자 한다면 인덱스를 삭제 후 새로 생성해고 기존 인덱스의 데이터를 재색인해야 한다.

```json lines
PUT my_index
{
  "settings": {
    "index": {
      "number_of_shards": 3,
      "number_of_replicas": 1
    }
  }
}
```

> `shrink API` or `split API` 를 사용하여 인덱스 재생성 없이 샤드 수를 조정할 수 있다고 하지만, 이또한 인덱스를 **close** 하고, 파일 재배치를 하는 복잡한 과정이 필요하다고 한다.

### `number_of_replicas`

- 인덱스의 `Replica` **복제본**의 수를 정하는 설정이다.
- 해당 설정은 인덱스 생성 이후에도 상시 변경이 가능하다.

```json lines
PUT my_index/_settings
{
  "number_of_replicas": 2
}
```

### `refresh_interval`

- `Elasticsearch` 에서 `Segment` **세그먼트**가 만들어지는 `refresh-time` 을 지정하는 설정이다.
- 기본적으로 `1s(1초)` 가 설정된다.
- 인덱스 생성 이후에도 상시 변경이 가능하다.

```json lines
PUT my_index
{
  "settings": {
    "refresh_interval": "30s"
  }
}
```

### `analyzer`, `tokenizer`, `filter`

- `Elasticsearch` 의 텍스트 분석을 위한 설정들도 [6. 데이터 색인과 텍스트 분석](./06_study_es_01.md)을 참고하면 될 것 같다.

---

## Mappings

### `_mappings`

- `_mappings` API 는 인덱스의 데이터가 입력되면, **데이터 타입 매핑 정보**에 대해 조회 가능하다.
- `Elasticsearch` 는 다른 데이터 저장소와 같이 다양한 데이터 타입을 가지고 있다.
- `Elasticsearch` 는 동적 매핑을 지원하기 때문에 별도 매핑 설정없이도 자동으로 매핑을 생성된다.

#### `_mappings` 정의

```json lines
PUT <인덱스명>
{
  "mappings": {
    "properties": {
      "<필드명>":{
        "type": "<필드 타입>"
      },
      // ...
    }
  }
}
```

#### `_mappings` 확인

```json lines
GET books/_mapping
```

---

### `Elasticsearch` 필드 매핑 타입 종류

- `Elasticsearch` 는 기본적으로 `Java` 에서 지원하는 타입과 루씬 레벨에서 추상화된 **확장 타입**으로 구성된다. 

|          구분          | 설명 |
|:--------------------:|----|
|        `text`        | -  |
|      `keyword`       | -  |
| `long`, `double` ... | -  |
|        `date`        | -  |
|      `boolean`       | -  |
|       `Object`       | -  |
|       `Nested`       | -  |
|        `Geo`         | -  |
|         `IP`         | -  |
|       `Range`        | -  |
|       `Binary`       | -  |

---

#### 출처
- [김종민님 - Elastic 가이드북](https://esbook.kimjmin.net/)
    - `Elastic` 회사의 개발자이셨던 **김종민님의 `Elastic 가이드북`** 을 주로 참고하여 문서 작성 계획
- [Elasticsearch in Action](https://www.manning.com/books/elasticsearch-in-action)