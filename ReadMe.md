# ParaTen

* This project implements the CP-ALS algorithm on Spark, it also implements related vector, matrix and tensor operation on Spark.
* The aim of this project is to utilize the power of RDD to achieve fast iteration while performing decomposition.
* This project is also a exploration of Type-Safe tensor operation, which is enlightened by [TensorSafe](https://github.com/MrVPlusOne/TensorSafe).

## Build and Run

### 1. Environment

* sbt 0.13.15
* Scala 2.11.11
* Spark 2.1.1

### 2. Compile and package

```bash
# package without dependencies
sbt package
```

It will produce package `target/scala-2.11/paraten_2.11-1.0.jar`.

```bash
# package with all dependencies
sbt assembly
```

You will get a big package `target/scala-2.11/ParaTen-assembly-1.0.jar`.

### 3. Usage

```bash
CP decomposition on Spark
Usage: ParaCP [options]

  -s, --shape <value>     shape
  -r, --rank <value>      rank
  --maxIter <value>       number of iterations of ALS. default: 500
  --tol <value>           tolerance for the ALS. default: 0.001
  --tries <value>         tries
  -o, --output-dir <dir>  output write path.
  -i, --input <value>     path of input file.
```

`-s, --shape`, `-r, --rank` and `-i, --input` are required.

### 4. Example call

Use the `spark-submit` to run ParaCP on cluster,

```bash
spark-submit \
--class org.chaomai.paraten.apps.ParaCP \
--master spark://Chaos-MacBook-Pro.local:7077 \
--total-executor-cores=4 \
--executor-memory=2g \
target/scala-2.11/ParaTen-assembly-1.0.jar \
-s 2,2,3,2 -r 5 \
-i hdfs://localhost:9000/user/chaomai/paraten/data/test_dim4_dense.tensor \
-o hdfs://localhost:9000/user/chaomai/paraten/result \
--maxIter 30 --tol 0.1 --tries 2
```

Also you can use `ParaCP_cluster_deploy_mode.sh` or `ParaCP_client_deploy_mode.sh` to run it. They accept same parameters.

```
[dim_1,..,dim_N (tensor)] [rank] [tensor file path] [output path] \
[max iteration] [tolerance] [tries] [master of Spark] \
[total-executor-cores] [executor-memory]
```

The first one submit with `--deploy-mode cluster`.

```bash
./src/main/scala/org/chaomai/paraten/apps/ParaCP_cluster_deploy_mode.sh \
2,2,3,2 5 \
hdfs://localhost:9000/user/chaomai/paraten/data/test_dim4_dense.tensor \
hdfs://localhost:9000/user/chaomai/paraten/result \
30 0.1 2 \
spark://Chaos-MacBook-Pro.local:6066 \
4 2g
```

### 5. Example decomposition

* original image

![lenna](https://github.com/ChaoMai/paraten/blob/master/src/test/resources/data/lenna_hires_head.jpg)

* reconstruct image by factor matrix

Set rank to 1000, iterate until converged. Pickup top k rank according to lambda vector and reconstruct the tensor i.e. the image.

![norm = \lVert\mathcal{X} - \mathcal{X}'\lVert = \sqrt{\sum^{I_1}_{i_1 = 1} \sum^{I_2}_{i_2 = 1} \cdots \sum^{I_N}_{i_N = 1} (x_{i_1 i_2 \cdots i_N} - x'_{i_1 i_2 \cdots i_N})^2}](https://latex.codecogs.com/svg.latex?norm&space;=&space;\lVert\mathcal{X}&space;-&space;\mathcal{X}'\lVert&space;=&space;\sqrt{\sum^{I_1}_{i_1&space;=&space;1}&space;\sum^{I_2}_{i_2&space;=&space;1}&space;\cdots&space;\sum^{I_N}_{i_N&space;=&space;1}&space;(x_{i_1&space;i_2&space;\cdots&space;i_N}&space;-&space;x'_{i_1&space;i_2&space;\cdots&space;i_N})^2})

| top k rank | norm | image |
| --- | --- | --- |
| 200 |  |  |
| 400 |  |  |
| 600 |  |  |
| 800 |  |  |
| 1000 |  |  |

## References

* An Investigation of Sparse Tensor Formats for Tensor Libraries. Parker Allen Tew. 2015.
* HaTen2: Billion-scale Tensor Decompositions. Inah Jeon, Evangelos E. Papalexakis, U Kang, Christos Faloutsos. 31st IEEE International Conference on Data Engineering (ICDE) 2015, Seoul, Korea.
* Scalable Tensor Decompositions for Multi-aspect Data Mining. Tamara G. Kolda, Jimeng Sun. Data Mining, 2008. ICDM '08.
* Tensor Decompositions and Applications. Tamara G. Kolda, Brett W. Bader. SIAM Review Volume 51 Issue 3, August 2009.

## Thanks

* [yuyongyang800/SparkDistributedMatrix](https://github.com/yuyongyang800/SparkDistributedMatrix)
* [MrVPlusOne/TensorSafe](https://github.com/MrVPlusOne/TensorSafe)
* [Mega-DatA-Lab/SpectralLDA-Spark](https://github.com/Mega-DatA-Lab/SpectralLDA-Spark)
* [mnick/scikit-tensor](https://github.com/mnick/scikit-tensor)

## License

[Apache License](https://github.com/ChaoMai/ParaTen/blob/master/LICENSE).
