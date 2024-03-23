# Product Service

## Add Product

```shell
curl --location --request POST 'http://localhost:8080/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "active": true,
    "created": 1626809248,
    "defaultPrice": 150.89,
    "description": "A great product.",
    "name": "Test Product",
    "shippable": true,
    "statementDescriptor": "Test Statement Descriptor",
    "taxCode": "TC1212",
    "unitLabel": "pcs",
    "updated": 1626809248,
    "url": "http://example.com/products/test-product"
}'
```

## Get All Products

```shell
curl --location --request GET 'http://localhost:8080/products' \
--header 'Content-Type: application/json'
```

## Get Product By ID

```shell
curl --location --request GET 'http://localhost:8080/products/ef58c3cd-174b-45ec-bfbe-9ba242ad0493' \
--header 'Content-Type: application/json'
```

##  Delete Product

```shell
curl --location --request DELETE 'http://localhost:8080/products/ef58c3cd-174b-45ec-bfbe-9ba242ad0493' \
--header 'Content-Type: application/json'
```
