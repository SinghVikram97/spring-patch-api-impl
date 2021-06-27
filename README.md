## Spring app with json-merge-patch support

### The Problem
https://medium.com/techno101/partial-updates-patch-in-spring-boot-63ff35582250

### Patch API
There are two RFCs on the JSON patch to support

JSON Merge Patch - https://tools.ietf.org/html/rfc7386 <br/>
JSON Patch - https://tools.ietf.org/html/rfc6902

We implement JSON merge patch with help of following library:
https://github.com/java-json-tools/json-patch

### Sample Usage
GET request to /person/2 returns <br>

``` 
{
    "id": "2",
    "firstName": "test",
    "lastName": "test",
    "address": {
        "district": "dwarka",
        "city": "Delhi",
        "country": "India"
    }
}
```

Send a PATCH request to /person/2 with the following payload

``` 
{
    "firstName": "Harry",
    "address": {
        "country": "Germany"
    }
}
```

The response as well as subsequent GET request to /person/2 will be updated values as shown
``` 
{
    "id": "2",
    "firstName": "Harry",
    "lastName": "test",
    "address": {
        "district": "dwarka",
        "city": "Delhi",
        "country": "Germany"
    }
}
```