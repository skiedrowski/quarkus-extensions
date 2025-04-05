package io.quarkus.hibernate.orm.rest.data.panache.kotlin.deployment

import io.restassured.RestAssured
import io.restassured.http.Header
import jakarta.ws.rs.core.Link
import org.assertj.core.api.Assertions
import org.assertj.core.api.ThrowingConsumer
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.util.*
import java.util.function.Predicate

abstract class AbstractGetMethodTest {
    @Test
    fun shouldNotGetNonExistentObject() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items/100")
            .then().statusCode(404)
    }

    @Test
    fun shouldGetSimpleObject() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items/1")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<Int?>(Matchers.equalTo<Int?>(1)))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("first")))
    }

    @Test
    fun shouldGetSimpleHalObject() {
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/items/1")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<Int?>(Matchers.equalTo<Int?>(1)))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("first")))
            .and().body("_links.add.href", Matchers.endsWith("/items"))
            .and().body("_links.list.href", Matchers.endsWith("/items"))
            .and().body("_links.self.href", Matchers.endsWith("/items/1"))
            .and().body("_links.update.href", Matchers.endsWith("/items/1"))
            .and().body("_links.remove.href", Matchers.endsWith("/items/1"))
    }

    @Test
    fun shouldGetComplexObject() {
        RestAssured.given().accept("application/json")
            .`when`().get("/collections/full")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("full")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("full collection")))
            .and().body("items.id", Matchers.contains<Int?>(1, 2))
            .and().body("items.name", Matchers.contains<String?>("first", "second"))
    }

    @Test
    fun shouldGetComplexHalObject() {
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/collections/full")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<String?>(Matchers.equalTo<String?>("full")))
            .and().body("name", Matchers.`is`<String?>(Matchers.equalTo<String?>("full collection")))
            .and().body("items.id", Matchers.contains<Int?>(1, 2))
            .and().body("items.name", Matchers.contains<String?>("first", "second"))
            .and().body("_links.add.href", Matchers.endsWith("/collections"))
            .and().body("_links.list.href", Matchers.endsWith("/collections"))
            .and().body("_links.self.href", Matchers.endsWith("/collections/full"))
            .and().body("_links.update.href", Matchers.endsWith("/collections/full"))
            .and().body("_links.remove.href", Matchers.endsWith("/collections/full"))
    }

    @Test
    fun shouldListSimpleObjects() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(1, 2))
            .and().body("name", Matchers.contains<String?>("first", "second"))
    }

    @Test
    fun shouldListWithPrimitiveFilter() {
        RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("type", 100)
            .get("/collections")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<String?>("empty", "full"))
    }

    @Test
    fun shouldListWithPrimitiveFilterAndNoResults() {
        RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("type", 99)
            .get("/collections")
            .then().statusCode(200)
            .and().body("id", Matchers.empty<Any?>())
    }

    @Test
    fun shouldListWithFilter() {
        RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("name", "first")
            .get("/items")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(1))
            .and().body("name", Matchers.contains<String?>("first"))
    }

    @Test
    fun shouldListWithManyFilters() {
        RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("id", 1)
            .queryParam("name", "first")
            .get("/items")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(1))
            .and().body("name", Matchers.contains<String?>("first"))
    }

    @Test
    fun shouldListWithNamedQuery() {
        RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("name", "s")
            .queryParam("namedQuery", "Item.containsInName")
            .get("/items")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(1, 2))
            .and().body("name", Matchers.contains<String?>("first", "second"))
    }

    @Test
    fun shouldListSimpleHalObjects() {
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/items")
            .then().statusCode(200)
            .and().body("_embedded.items.id", Matchers.contains<Int?>(1, 2))
            .and().body("_embedded.items.name", Matchers.contains<String?>("first", "second"))
            .and().body("_embedded.items._links.add.href", Matchers.contains<String?>(Matchers.endsWith("/items"), Matchers.endsWith("/items")))
            .and().body("_embedded.items._links.list.href", Matchers.contains<String?>(Matchers.endsWith("/items"), Matchers.endsWith("/items")))
            .and().body("_embedded.items._links.self.href", Matchers.contains<String?>(Matchers.endsWith("/items/1"), Matchers.endsWith("/items/2")))
            .and().body("_embedded.items._links.update.href", Matchers.contains<String?>(Matchers.endsWith("/items/1"), Matchers.endsWith("/items/2")))
            .and().body("_embedded.items._links.remove.href", Matchers.contains<String?>(Matchers.endsWith("/items/1"), Matchers.endsWith("/items/2")))
            .and().body("_links.add.href", Matchers.endsWith("/items"))
            .and().body("_links.list.href", Matchers.endsWith("/items"))
    }

    @Test
    fun shouldListSimpleAscendingObjects() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items?sort=name,id")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(1, 2))
            .and().body("name", Matchers.contains<String?>("first", "second"))
    }

    @Test
    fun shouldListSimpleDescendingObjects() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items?sort=-name,id")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(2, 1))
            .and().body("name", Matchers.contains<String?>("second", "first"))
    }

    @Test
    fun shouldListSimpleDescendingObjectsAndFilter() {
        RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("name", "first")
            .get("/items?sort=-name,id")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<Int?>(1))
            .and().body("name", Matchers.contains<String?>("first"))
    }

    @Test
    fun shouldNotListWithInvalidSortParam() {
        RestAssured.given().accept("application/json")
            .`when`().get("/items?sort=1name")
            .then().statusCode(400)
            .and().body(Matchers.`is`<String?>(Matchers.equalTo<String?>("Invalid sort parameter '1name'")))
    }

    @Test
    fun shouldNotListHalWithInvalidSortParam() {
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/items?sort=1name")
            .then().statusCode(400)
            .and().body(Matchers.`is`<String?>(Matchers.equalTo<String?>("Invalid sort parameter '1name'")))
    }

    @Test
    fun shouldListComplexObjects() {
        RestAssured.given().accept("application/json")
            .`when`().get("/collections")
            .then().statusCode(200)
            .and().body("id", Matchers.contains<String?>("empty", "full"))
            .and().body("name", Matchers.contains<String?>("empty collection", "full collection"))
            .and().body("items.id[0]", Matchers.`is`<MutableCollection<*>?>(Matchers.empty<Any?>()))
            .and().body("items.id[1]", Matchers.contains<Int?>(1, 2))
            .and().body("items.name[1]", Matchers.contains<String?>("first", "second"))
    }

    @Test
    fun shouldListComplexHalObjects() {
        RestAssured.given().accept("application/hal+json")
            .`when`().get("/collections")
            .then().statusCode(200)
            .and().body("_embedded.item-collections.id", Matchers.contains<String?>("empty", "full"))
            .and().body("_embedded.item-collections.name", Matchers.contains<String?>("empty collection", "full collection"))
            .and().body("_embedded.item-collections.items.id[0]", Matchers.`is`<MutableCollection<*>?>(Matchers.empty<Any?>()))
            .and().body("_embedded.item-collections.items.id[1]", Matchers.contains<Int?>(1, 2))
            .and().body("_embedded.item-collections.items.name[1]", Matchers.contains<String?>("first", "second"))
            .and()
            .body(
                "_embedded.item-collections._links.add.href",
                Matchers.contains<String?>(Matchers.endsWith("/collections"), Matchers.endsWith("/collections"))
            )
            .and()
            .body(
                "_embedded.item-collections._links.list.href",
                Matchers.contains<String?>(Matchers.endsWith("/collections"), Matchers.endsWith("/collections"))
            )
            .and()
            .body(
                "_embedded.item-collections._links.self.href",
                Matchers.contains<String?>(Matchers.endsWith("/collections/empty"), Matchers.endsWith("/collections/full"))
            )
            .and()
            .body(
                "_embedded.item-collections._links.update.href",
                Matchers.contains<String?>(Matchers.endsWith("/collections/empty"), Matchers.endsWith("/collections/full"))
            )
            .and()
            .body(
                "_embedded.item-collections._links.remove.href",
                Matchers.contains<String?>(Matchers.endsWith("/collections/empty"), Matchers.endsWith("/collections/full"))
            )
            .and().body("_links.add.href", Matchers.endsWith("/collections"))
            .and().body("_links.list.href", Matchers.endsWith("/collections"))
    }

    @Test
    fun shouldNotGetNonExistentPage() {
        RestAssured.given().accept("application/json")
            .and().queryParam("page", 100)
            .`when`().get("/items")
            .then().statusCode(200)
            .and().body("id", Matchers.`is`<MutableCollection<*>?>(Matchers.empty<Any?>()))
    }

    @Test
    fun shouldNotGetNegativePageOrSize() {
        RestAssured.given().accept("application/json")
            .and().queryParam("page", -1)
            .and().queryParam("size", -1)
            .`when`().get("/items")
            .then().statusCode(200) // Invalid page and size parameters are replaced with defaults
            .and().body("id", Matchers.contains<Int?>(1, 2))
    }

    @Test
    fun shouldGetFirstPage() {
        val response = RestAssured.given().accept("application/json")
            .and().queryParam("page", 0)
            .and().queryParam("size", 1)
            .`when`().get("/items")
            .thenReturn()
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("id")).containsOnly(1)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("name")).containsOnly("first")

        val links: MutableList<Link?> = LinkedList<Link?>()
        for (header in response.getHeaders().getList("Link")) {
            links.add(Link.valueOf(header.getValue()))
        }
        Assertions.assertThat<Link?>(links).hasSize(3)
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=0&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("first")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=1&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("last")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=1&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("next")
        })
    }

    @Test
    fun shouldGetFirstPageWithFilter() {
        val response = RestAssured.given().accept("application/json")
            .and().queryParam("page", 0)
            .and().queryParam("size", 1)
            .and().queryParam("name", "second")
            .`when`().get("/items")
            .thenReturn()
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("id")).containsOnly(2)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("name")).containsOnly("second")
    }

    @Test
    fun shouldGetFirstHalPage() {
        val response = RestAssured.given().accept("application/hal+json")
            .and().queryParam("page", 0)
            .and().queryParam("size", 1)
            .`when`().get("/items")
            .thenReturn()
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("_embedded.items.id")).containsOnly(1)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("_embedded.items.name")).containsOnly("first")
        Assertions.assertThat(response.body().jsonPath().getString("_links.add.href")).endsWith("/items")
        Assertions.assertThat(response.body().jsonPath().getString("_links.list.href")).endsWith("/items")
        Assertions.assertThat(response.body().jsonPath().getString("_links.first.href")).endsWith("/items?page=0&size=1")
        Assertions.assertThat(response.body().jsonPath().getString("_links.last.href")).endsWith("/items?page=1&size=1")
        Assertions.assertThat(response.body().jsonPath().getString("_links.previous.href")).isNull()
        Assertions.assertThat(response.body().jsonPath().getString("_links.next.href")).endsWith("/items?page=1&size=1")

        val links: MutableList<Link?> = LinkedList<Link?>()
        for (header in response.getHeaders().getList("Link")) {
            links.add(Link.valueOf(header.getValue()))
        }
        Assertions.assertThat<Link?>(links).hasSize(3)
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=0&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("first")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=1&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("last")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=1&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("next")
        })
    }

    @Test
    fun shouldGetLastPage() {
        val response = RestAssured.given().accept("application/json")
            .and().queryParam("page", 1)
            .and().queryParam("size", 1)
            .`when`().get("/items")
            .thenReturn()
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("id")).containsOnly(2)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("name")).containsOnly("second")

        val links: MutableList<Link?> = LinkedList<Link?>()
        for (header in response.getHeaders().getList("Link")) {
            links.add(Link.valueOf(header.getValue()))
        }
        Assertions.assertThat<Link?>(links).hasSize(3)
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=0&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("first")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=1&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("last")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=0&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("previous")
        })
    }

    @Test
    fun shouldGetLastHalPage() {
        val response = RestAssured.given().accept("application/hal+json")
            .and().queryParam("page", 1)
            .and().queryParam("size", 1)
            .`when`().get("/items")
            .thenReturn()
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("_embedded.items.id")).containsOnly(2)
        Assertions.assertThat<Any?>(response.body().jsonPath().getList<Any?>("_embedded.items.name")).containsOnly("second")
        Assertions.assertThat(response.body().jsonPath().getString("_links.add.href")).endsWith("/items")
        Assertions.assertThat(response.body().jsonPath().getString("_links.list.href")).endsWith("/items")
        Assertions.assertThat(response.body().jsonPath().getString("_links.first.href")).endsWith("/items?page=0&size=1")
        Assertions.assertThat(response.body().jsonPath().getString("_links.last.href")).endsWith("/items?page=1&size=1")
        Assertions.assertThat(response.body().jsonPath().getString("_links.previous.href")).endsWith("/items?page=0&size=1")
        Assertions.assertThat(response.body().jsonPath().getString("_links.next.href")).isNull()

        val links: MutableList<Link?> = LinkedList<Link?>()
        for (header in response.getHeaders().getList("Link")) {
            links.add(Link.valueOf(header.getValue()))
        }
        Assertions.assertThat<Link?>(links).hasSize(3)
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=0&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("first")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=1&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("last")
        })
        Assertions.assertThat<Link?>(links).anySatisfy(ThrowingConsumer { link: Link? ->
            Assertions.assertThat(link!!.getUri().toString()).endsWith("/items?page=0&size=1")
            Assertions.assertThat(link.getRel()).isEqualTo("previous")
        })
    }

    @Test
    fun shouldListEmptyTables() {
        RestAssured.given().accept("application/hal+json")
            .and().queryParam("page", 1)
            .and().queryParam("size", 1)
            .`when`().get("/empty-list-items")
            .then().statusCode(200)
    }

    @ParameterizedTest
    @CsvSource(
        "page,0", "size,1", "name,first", "collection.id,full"
    )
    fun shouldShowSpecificParameterInLinkHeaders(queryParamName: String?, queryParamValue: String?) {
        val response = RestAssured.given().accept("application/json")
            .`when`()
            .queryParam(queryParamName, queryParamValue)
            .get("/items")
            .thenReturn()

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        val links = response.getHeaders().getList("Link")
            .stream()
            .map<Link?> { header: Header? -> Link.valueOf(header!!.getValue()) }
            .toList()
        Assertions.assertThat<Link?>(links).allMatch(Predicate { link: Link? ->
            link!!.getUri().getQuery()
                .contains(String.format("%s=%s", queryParamName, queryParamValue))
        })
    }

    @Test
    fun shouldShowAllPaginationAndCustomQueryParametersInLinkHeaders() {
        val response = RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("page", "0")
            .queryParam("size", "3")
            .queryParam("name", "first")
            .queryParam("namedQuery", "Item.containsInName")
            .get("/items")
            .thenReturn()

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        val links = response.getHeaders().getList("Link")
            .stream()
            .map<Link?> { header: Header? -> Link.valueOf(header!!.getValue()) }
            .toList()
        Assertions.assertThat<Link?>(links).allMatch(Predicate { link: Link? ->
            link!!.getUri().getQuery()
                .contains("page=0&size=3&namedQuery=Item.containsInName&name=first")
        })
    }

    @Test
    fun shouldShowAllPaginationAndFilteringParametersInLinkHeaders() {
        val response = RestAssured.given().accept("application/json")
            .`when`()
            .queryParam("page", "0")
            .queryParam("size", "1")
            .queryParam("name", "first")
            .queryParam("collection.id", "full")
            .get("/items")
            .thenReturn()

        Assertions.assertThat(response.getStatusCode()).isEqualTo(200)
        val links = response.getHeaders().getList("Link")
            .stream()
            .map<Link?> { header: Header? -> Link.valueOf(header!!.getValue()) }
            .toList()
        Assertions.assertThat<Link?>(links).allMatch(Predicate { link: Link? ->
            val query = link!!.getUri().getQuery()
            query.contains("page=0&size=1") && query.contains("name=first") && query.contains("collection.id=full")
        })
    }
}
