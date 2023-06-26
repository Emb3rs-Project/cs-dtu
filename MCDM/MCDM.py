import streamlit as st  #For build and share web apps

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt #For graphical comparison and analysis
import re
import altair as alt

ahp_description = """
Here we provide an accessible and user-friendly platform to apply the Analytic Hierarchy Process (AHP) for multi-criteria decision making. This tool empowers users to determine the most suitable strategies for various decision-making scenarios, such as optimizing excess heat utilization in an industrial setting. Users can input their own criteria, sub-criteria, and alternatives, and assign their respective importance through pairwise comparisons. The tool then calculates the priority weights of each element and presents the final rankings of the alternatives. Additionally, it offers users the ability to adjust the weights and observe changes in the results, providing a dynamic and flexible decision-making environment. This user-oriented tool is designed to facilitate informed decision-making by providing a systematic, transparent, and adaptable approach to evaluating multiple alternatives based on a wide range of considerations."""

importance_level_desc = """
The values from 1 to 9 in the pairwise comparison matrix of the Analytic Hierarchy Process (AHP) represent the level of importance or preference of one element over another, based on the decision-maker's judgement. Here's what each value signifies:

- **1**: Equal importance or preference. Both elements contribute equally to the objective.
- **2 to 4**: Moderate to strong importance or preference of one element over another. 
    - **2**: Weak or slight preference for one element over another.
    - **3**: Moderate preference for one element over another.
    - **4**: Moderate plus preference. It's between moderate and strong preference.
- **5**: Strong or essential importance. The preferred element is strongly favored and its dominance is demonstrated in practice.
- **6 to 7**: Very strong to demonstrated importance or preference of one element over another. 
    - **6**: Strong plus preference. It's between strong and very strong preference.
    - **7**: Very strong preference. The preferred element is strongly favored and its dominance is demonstrated in practice.
- **8**: Very, very strong preference. It's between very strong and extreme preference.
- **9**: Extreme importance or preference. The evidence favoring one element over another is of the highest possible order of affirmation.

Please note that values between the scale steps may also be used if you wish to capture more subtle differences in judgement (i.e., 1.2, 2.5, 4.3, etc.). Also, for inverse importance, you can use the reciprocal values (i.e., if element A has one third of the importance of element B, the preference of A to B would be 1/3).
"""

@st.cache_data
def get_weight(A, str):
    n = A.shape[0]
    e_vals, e_vecs = np.linalg.eig(A)
    lamb = max(e_vals)
    w = e_vecs[:, e_vals.argmax()]
    w = w / np.sum(w)  # Normalization
    # Consistency Checking
    ri = {1: 0, 2: 0, 3: 0.58, 4: 0.9, 5: 1.12, 6: 1.24,
          7: 1.32, 8: 1.41, 9: 1.45, 10: 1.49, 11: 1.51}
    ci = (lamb - n) / (n - 1)
    cr = ci / ri[n]
    print("The normalized eigen vector:")
    print(w)
    print('CR = %f' % cr)
    if cr >= 0.1:
        print("Failed Consistency check of "+str)
        st.error("Failed Consistency check of "+str)

    return w


def plot_graph1(x, y, ylabel, title):
    # Create a horizontal bar chart
    fig, ax = plt.subplots()
    ax.bar(y, x, color='#FF4B4B')
    ax.set_facecolor('#F0F2F6')
    # Set title and axis labels
    ax.set_title(title)
    ax.set_xlabel(ylabel)
    ax.set_ylabel("Values")
    return fig



def plot_graph(x, y, xlabel, ylabel, title):
    # Create a horizontal bar chart
    data = pd.DataFrame({'x': x, 'y': y})
    chart = alt.Chart(data).mark_bar(color='#FF4B4B').encode(
        x=alt.X('x', sort='-y', axis=alt.Axis(title=xlabel, labelAngle=0, labelLimit=200)),  # Rotate the x-axis labels by 90 degrees
        y=alt.Y('y', axis=alt.Axis(title=ylabel))
    ).properties(
        title=title,
        width=200,
        height=400
    ).configure_legend(labelLimit= 0)
    
    chart.configure_axis(
        labelFontSize=14,
        titleFontSize=16,
    )
    chart.configure_title(
        fontSize=20
    )
    return chart

@st.cache_data
def calculate_ahp(A, B, n, m, criterias, alternatives):

    for i in range(0, n):
        for j in range(i, n):
            if i != j:
                A[j][i] = float(1/A[i][j])
    # print("A : ")
    # print(str(A))
    dfA = pd.DataFrame(A)
    # Use tabel instead of dataframe because dataframe are interactable
    st.markdown(" #### Criteria Table")
    st.table(dfA)
    for k in range(0, n):
        for i in range(0, m):
            for j in range(i, m):
                if i != j:
                    B[k][j][i] = float(1/B[k][i][j])
    # print("B : ")
    # print(str(B))
    st.write("---")
    for i in range(0, n):
        dfB = pd.DataFrame(B[i])
        # Use tabel instead of dataframe because dataframe are interactable
        st.markdown(" #### Alternative Table for Criterion " + criterias[i])
        st.table(dfB)
    W2 = get_weight(A, "Criteria Table")
    W3 = np.zeros((n, m))
    for i in range(0, n):
        w3 = get_weight(B[i], "Alternatives Table for Criterion "+ criterias[i])
        W3[i] = w3
    W = np.dot(W2, W3)
    
    chart1 = plot_graph(criterias, W2, "Criteria", "Normalized weight", "Weights of Criteria")
    st.altair_chart(chart1, use_container_width=True)
    #st.pyplot(chart)
    
    chart2 = plot_graph(alternatives, W, "Alternatives", "Normalized weight", "Optimal Alternative for given Criteria")
    st.altair_chart(chart2, use_container_width=True)
    #st.pyplot()
    st.balloons()


def main():
    #st.set_page_config(page_title="Heat Utilization Optimizer: An AHP-based Decision Support Tool", page_icon=":bar_chart:")
    st.set_page_config(
     page_title="Heat Utilization Optimizer: An AHP-based Decision Support Tool",
     page_icon="🧊",
     layout="wide",
     initial_sidebar_state="expanded",
     menu_items={
         'Report a bug': "http://xiufengliu.github.io",
         'About': "AHP, an MCDM-based decision tool"
         }
     )
    st.header("Heat Utilization Optimizer: An AHP-based Decision Support Tool")
    st.write(ahp_description)
    with st.expander("Importance level (Click ⬇️ for more details)"):
        st.markdown(importance_level_desc)
        
    st.sidebar.image("emb3rs-logo.png", use_column_width=True)
    st.sidebar.title("Criteria & Alternatives")
    cri = st.sidebar.text_input("Enter Criteria")
    alt = st.sidebar.text_input("Enter Alternatives")
    criterias = [c.strip() for c in re.split(',|;|\t', cri)]
    alternatives = [a.strip() for a in re.split(',|;|\t', alt)]
    st.sidebar.info("Enter multiple values of Criteria & Alternatives, seprated by ; or ,")
    st.sidebar.info("Example: Source fluid type, capacity, cost")

    if cri and alt:
        with st.expander("Criteria Weights"):
            st.subheader("Pairwise comparision for Criteria")
            st.write("---")
            n = len(criterias)
            A = np.zeros((n, n))
            Aradio = np.zeros((n,n))
            for i in range(0, n):
                for j in range(i, n):
                    if i == j:
                        A[i][j] = 1
                    else:
                        
                        st.markdown(" ##### Criterion "+criterias[i] + " comparision with Criterion " +criterias[j])
                        criteriaradio = st.radio("Select higher priority criterion ",(criterias[i], criterias[j],), horizontal=True)
                        if criteriaradio == criterias[i]:
                            A[i][j] = st.slider(label="how much higher "+ criterias[i] +" is in comparision with "+criterias[j]+ " ?",min_value= 1,max_value= 9,value= 1)
                            A[j][i] = float(1/A[i][j])
                        else:
                            A[j][i] = st.slider(label="how much higher "+ criterias[j] +" is in comparision with "+criterias[i]+ " ?",min_value= 1,max_value= 9,value= 1)
                            A[i][j] = float(1/A[j][i])
                
        with st.expander("Alternative Weights"):
            st.subheader("Pairwise comparision for Alternatives")
            m = len(alternatives)
            B = np.zeros((n, m, m))

            for k in range(0, n):
                st.write("---")
                st.markdown(" ##### Alternative comparision for Criterion "+criterias[k])
                for i in range(0, m):
                    for j in range(i, m):
                        if i == j:
                            B[k][i][j] = 1
                        else:
                            alternativeradio = st.radio("Select higher priority alternative for criteria "+criterias[k] ,(alternatives[i], alternatives[j],), horizontal=True)
                            if alternativeradio == alternatives[i]:
                                B[k][i][j] = st.slider("Considering Criterion " + criterias[k] + ", how much higher " + alternatives[i] + " is in comparision with " + alternatives[j]+" ?", 1, 9, 1)
                                B[k][j][i] = float(1/B[k][i][j])
                            else:
                                B[k][j][i] = st.slider("Considering Criterion " + criterias[k] + ", how much higher " + alternatives[j] + " is in comparision with " + alternatives[i]+" ?", 1, 9, 1)
                                B[k][i][j] = float(1/B[k][j][i])

                
        btn = st.button("Calculate AHP")
        st.write("##")
        if btn:
            calculate_ahp(A, B, n, m, criterias, alternatives)


if __name__ == '__main__':
    main()
