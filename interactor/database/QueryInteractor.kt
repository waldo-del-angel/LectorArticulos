package com.limonalmacenes.interactor.database

import android.os.AsyncTask
import com.limonalmacenes.interfaces.query.QueryPresenterListener
import java.lang.Exception
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException

class QueryInteractor: AsyncTask<Void, Int, ArrayList<MutableList<Any>>>() {
    private lateinit var queryPresenterListener: QueryPresenterListener
    private lateinit var busqueda: String
    private var tipo_busqueda = 0
    private lateinit var query: String

    private var prepareStatement: PreparedStatement? = null
    private var resultSet: ResultSet? = null
    private var metadata: ResultSetMetaData? = null

    private var ocurrioError = false

    fun conexionPresenter(queryPresenterListener: QueryPresenterListener, busqueda: String, tipo_busqueda: Int){
        this.queryPresenterListener = queryPresenterListener
        this.busqueda = busqueda
        this.tipo_busqueda = tipo_busqueda
    }

    override fun onPreExecute() {
        super.onPreExecute()
        queryPresenterListener.showLoadingElement()
    }

    override fun doInBackground(vararg params: Void?): ArrayList<MutableList<Any>> {
        when (tipo_busqueda){
            1 -> query = "SELECT * FROM MOVILARTICULOSPORCODBARRAS('$busqueda', 2);"
            2 -> query = "SELECT * FROM MOVILARTICULOSPORIDENTIFICACION('$busqueda', 2);"
            3 -> query = "SELECT * FROM MOVILARTICULOSPORDESCRIPCION('%$busqueda%', 2);"
        }
        return consulta(query)
    }

    override fun onPostExecute(result: ArrayList<MutableList<Any>>) {
        super.onPostExecute(result)
        if(ocurrioError){
            queryPresenterListener.serverError()
        } else {
            if(result.isEmpty()){
                queryPresenterListener.resultNotFound()
            } else {
                queryPresenterListener.setResultado(result)
            }
        }
    }

    private fun consulta(query: String): ArrayList<MutableList<Any>> {
        try{
            prepareStatement = Firebird.connection()?.prepareStatement(query)
            resultSet = prepareStatement?.executeQuery()
            metadata = resultSet?.metaData
            val columnas = metadata?.columnCount
            val resultadoConsulta = ArrayList<MutableList<Any>>()
            while (resultSet?.next()!!) {
                val fila = mutableListOf<Any>()
                for (i in 1..columnas!!) resultSet?.getObject(i).let { fila.add(it!!) }
                resultadoConsulta.add(fila)
            }
            prepareStatement?.close()
            ocurrioError = false
            return resultadoConsulta
        } catch(sqlerror: SQLException){
            return ArrayList()
        } catch (error: Exception) {
            ocurrioError = true
            return ArrayList()
        }
    }
}